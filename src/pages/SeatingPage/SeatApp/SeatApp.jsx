import React, { Component, useState } from 'react';
import './SeatApp.css';
import Seat from '../Seat/Seat.js';
import axios, { formToJSON } from 'axios';
import { json, useParams } from 'react-router-dom';
import PG from "../../HomePage/images/PG.png"
import Button from '@mui/material/Button'
import Timer from './Timer';
import { useNavigate } from "react-router-dom";
import { recomposeColor } from '@mui/material';

class SeatApp extends Component {
  template = function (row, coloumn, type, availability) { }
  Unavailable = [];
  d = new Date();
  constructor(props) {
    super(props);
    this.state = { seats: [], chosenSeats: [], showSummary: false };
  }


  async componentDidMount() {

    let seats = [];

    //How many seats to create r=row i=col
    for (let r = 0; r < 4; r++) {
      for (let i = 0; i < 14; i++) {
        seats.push({ row: r, num: i, avail: true });
      }
    }

    const moviename = localStorage.getItem("movieTitle");
    console.log(moviename);
    await axios.get("http://localhost:8080/api/v1/seats/OccupiedSeats/" + moviename)
      .then(json => json.data.forEach(data => this.Unavailable.push([data.row, data.coloumn])))
      .catch(console.error);

    //console.log(this.Unavailable);

    //code to make seat unavail r = row c = col
    for (let i = 0; i < seats.length; i++) {
      for (let j = 0; j < this.Unavailable.length; j++) {
        let r = this.Unavailable[j][0];
        let c = this.Unavailable[j][1];
        if (seats[i].row === r && seats[i].num === c) {
          seats[i].avail = false;
        }
      }
    }

    //Updating
    this.setState({ seats });
  }

  handleSeatSelect = (row, col) => {
    const { seats } = this.state;
    //object of the seat that i clicked
    const selectedSeat = seats.find(seat => seat.row === row && seat.num === col);

    //A list of selected seat numbers
    const selectedSeatNumbers = [];
    seats.forEach(seat => {
      if (seat.row === row && seat.selected) {
        selectedSeatNumbers.push(seat.num);
      }
    });

    //Seat picking algo
    const updatedSeats = seats.map(seat => {
      if (seat.row === row && seat.num === col) {
        if (selectedSeatNumbers.includes(col)) {
          // If clicked seat is in selected group
          if (selectedSeatNumbers.length > 1) {
            // Check if it's the maximum or minimum seat number in the group
            let maxSelected = Math.max(...selectedSeatNumbers);
            let minSelected = Math.min(...selectedSeatNumbers);
            //Only allow the outsides to be 'de-selected'
            if (col === maxSelected || col === minSelected) {
              seat.selected = !seat.selected;
            }
            else {
              // Show pop-up message
              alert("Please choose a seat with no space between.");
            }
          }
          else {
            seat.selected = !seat.selected;
          }
        }
        else if (
          selectedSeatNumbers.length === 0 || selectedSeatNumbers.includes(col - 1) ||
          selectedSeatNumbers.includes(col + 1) || selectedSeatNumbers.includes(col)
        ) {
          // Check if the number of selected seats exceeds 10
          if (selectedSeatNumbers.length < 10) {
            seat.selected = !seat.selected;
          } else {
            // Show pop-up message
            alert("You cannot book more than 10 seats.");
          }
        }
        else {
          // Show pop-up message
          alert("Please choose a seat with no space between.");
        }
      }
      return seat;
    });

    // Update chosenSeats array
    const updatedChosenSeats = seats.filter((seat) => seat.selected).map((seat) => seat.num + 1);

    this.setState({
      seats: updatedSeats,
      chosenSeats: updatedChosenSeats
    });

    //log of selected/unselected seat
    if (selectedSeat.selected) {
      console.log(`Selected seat: {row: ${row}, column: ${col}}`);
    }
    else {
      console.log(`Unselected seat: {row: ${row}, column: ${col}}`);
    }
  }

  handleCheckout = () => {
    const { chosenSeats } = this.state;
    this.setState({ showSummary: true });
  };

  handleCancel = () => {
    const { chosenSeats } = this.state;
    this.setState({ showSummary: false });
  }


  //Handling confirm button to give info to backend
  handleConfirm = async () => {
    const accessToken = localStorage.getItem('accessToken');
    const username = localStorage.getItem('username');
    if (accessToken == null || username == null) {
      alert("please login first before booking");
      window.location.href = '/register'
      return;
    }
    const headers = {
      'Authorization': `Bearer ${accessToken}`
    };

    const { seats } = this.state;
    // grabbing username
    const selectedSeats = seats.filter(seat => seat.selected)
      .map(seat => {
        const { row, num } = seat;
        return { row, num };
      })

    const movieName = localStorage.getItem("movieTitle");
    console.log(movieName);
    //adding purchased seats to backend
    await selectedSeats.forEach(seat => this.addSeatToDB(seat, username, movieName));
    //TO DO: find better way to find seat object, username does not work
    //grabing the seat ids created
    //let seatIDs = [];
    // const findseatstring = "http://localhost:8080/api/v1/seats/findSeats/" + username
    // await axios.get(findseatstring, { headers })
    //   .then(json => json.data.forEach(data => seatIDs.push(data._id)))
    //   .catch(console.error);
    // console.log(seatIDs);
    // creating purchase object
    //await this.addPurchaseOrder(username, seatIDs, movieName);
    const rowName = [];
    for (let i = 0; i < seats.length; i++) {
      let temp = 'A'.charCodeAt(0) + i;
      rowName.push(String.fromCharCode(temp));
    }
    window.location.href = '/';
    let selectedSeatsString = "";
    selectedSeats.forEach(seat => {
      seat.num += 1;  // Increment seat.num by 1
      selectedSeatsString = selectedSeatsString + rowName[seat.row] + seat.num + ', ';
    })
    const alertMessage = "Your seats: " + selectedSeatsString + "for the movie: " + movieName + " are booked!";
    alert(alertMessage);

  }

  addSeatToDB = (seat, username, movieName) => {
    try {
      const accessToken = localStorage.getItem('accessToken');
      const headers = {
        'Authorization': `Bearer ${accessToken}`
      };
      axios.post("http://localhost:8080/api/v1/seats/PostSeats", {
        row: seat.row,
        coloumn: seat.num,
        type: "standard",
        availability: false,
        username: username,
        movieName: movieName
      }, {
        headers: headers,
        validateStatus: function (status) {

          return true; // Resolve only if the status code is less than 500
        }
      }).then(this.Unavailable.push([seat.row, seat.num]));
    } catch {
      alert("how did you get here? please report steps done to team");
    }


    console.log(this.Unavailable);
  }

  addPurchaseOrder = (username, seatIDs, movieName) => {
    try {
      const accessToken = localStorage.getItem('accessToken');
      const headers = {
        'Authorization': `Bearer ${accessToken}`
      };
      axios.post("http://localhost:8080/api/purchases/postPurchase", {
        userId: username,
        movieId: movieName,
        //seatIds: seatIDs,
      }, { headers: headers })
        .then();
    } catch {
      alert("how did you get here? please report steps done to team");
    }
    console.log("purchase add (without seat id list) success")
  }


  render() {
    const { seats, showSummary } = this.state;

    // Movie Image url
    const movieImage = localStorage.getItem("movieImage");

    // Movie title
    const title = localStorage.getItem("movieTitle");

    console.log(title);

    //create array with nulls
    const seatsGrid = [];
    for (let i = 0; i < 4; i++) {
      let row = [];
      for (let j = 0; j < 14; j++) {
        row.push(null);
      }
      seatsGrid.push(row);
    }

    for (let i = 0; i < seats.length; i++) {
      let seat = seats[i];
      seatsGrid[seat.row][seat.num] = (
        <Seat
          row={seat.row}
          num={seat.num}
          avail={seat.avail}
          selected={seat.selected}
          onSeatSelect={this.handleSeatSelect}
          key={seat.row + seat.num}
        />
      );
    }

    //This code allows for conversion of row from int to char
    const rowName = [];
    for (let i = 0; i < seatsGrid.length; i++) {
      let temp = 'A'.charCodeAt(0) + i;
      rowName.push(String.fromCharCode(temp));
    }

    const chosenSeats = seats.filter(seat => seat.selected).map(seat => seat.num + 1);
    const chosenRow = seats.filter(seat => seat.selected).map(seat => seat.row + 1);
    const availableSeats = seats.filter(seat => seat.avail && !seat.selected).length;
    const location = localStorage.getItem("selectedLoc");
    const time = localStorage.getItem("selectedTime");
    const currentDate = new Date().toLocaleDateString();


    return (
      <div style={{ backgroundColor: 'black' }}>
        <div className='minibox' style={{ display: showSummary ? 'none' : 'block' }}>
          <div className='movieInfo'>
            <div className='imageContainer'>
              <img src={`https://image.tmdb.org/t/p/original/${movieImage}`} alt="" style={{ height: "200px", width: "auto" }} />
            </div>
            <div align='left'>
              <h1>{title}</h1>
              <p>{location}</p>
              <p>{currentDate}</p>
              <p>{time}</p>
            </div>
          </div>
          <div id="stage-container">
            <div><span><Timer /></span></div>
            <svg width="500" height="100" >
            </svg>
            <h1 id="stage">Screen</h1>
          </div>

          <div className='layout'>
            <div className='left'>
              {rowName.map((rowLabel, rowIndex) => (
                <div className="Row" key={rowIndex}>
                  {rowLabel}
                  {seatsGrid[rowIndex].slice(0, 4)}
                </div>
              ))}
            </div>

            <div className='center'>
              {[0, 1, 2, 3].map(rowIndex => (
                <div className="Row" key={rowIndex}>
                  {seatsGrid[rowIndex].slice(4, 10)}
                </div>
              ))}
            </div>

            <div className='right'>
              {[0, 1, 2, 3].map(rowIndex => (
                <div className="Row" key={rowIndex}>
                  {seatsGrid[rowIndex].slice(10, 14)}
                </div>
              ))}
            </div>
          </div>

          <div>
            <h3>Legend</h3>
            <pre>
              <div id='Unavailable' style={{ display: 'inline-block' }}></div>Unavailable
              <div id='AvailableLegend' style={{ display: 'inline-block', marginLeft: '10px' }}></div>Available
              <div id='selected' style={{ display: 'inline-block', marginLeft: '10px' }}></div>Selected
            </pre>
          </div>
        </div>

        <footer class="footer" style={{ display: showSummary ? 'none' : 'block' }}>
          <div className='inputs'>
            <pre>
              Quantity: {chosenSeats.length}
            </pre>
            <pre>
              Cost: ${chosenSeats.length * 8}
            </pre>
            <Button variant="contained" onClick={this.handleCheckout} disabled={chosenSeats.length === 0 ? true : false} style={{ background: 'grey' }}>checkout</Button>
          </div>
        </footer>

        <div className='orderSummary' style={{ display: showSummary ? 'block' : 'none' }}>
          <Timer />
          <pre>
            Qty: {chosenSeats.length}
          </pre>
          <pre>
            Seats: {chosenSeats.map((seatNum, index) => {
              const rowLabel = rowName[chosenRow[index] - 1];
              return `${rowLabel}${seatNum}`;
            }).join(", ")}
          </pre>
          <pre>
            Total cost: ${chosenSeats.length * 8}
          </pre>
          <Button variant="contained" onClick={this.handleCancel} style={{ background: 'grey' }}>Cancel</Button>
          <Button variant="contained" onClick={this.handleConfirm} style={{ background: 'grey' }}>Confirm</Button>
        </div>

      </div>
    );
  }
}

export default SeatApp;
