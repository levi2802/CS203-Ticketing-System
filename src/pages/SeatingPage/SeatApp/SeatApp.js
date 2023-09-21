import React, { Component, useState } from 'react';
import './SeatApp.css';
import Seat from '../Seat/Seat.js';
import axios, { formToJSON } from 'axios';
import { json } from 'react-router-dom';
import sample1 from "../../HomePage/images/image1.png";
import PG from "../../HomePage/images/PG.png"
import Button from '@mui/material/Button'
import Timer from './Timer';

class SeatApp extends Component {
  template = function (row, coloumn, type, availability) { }
  Unavailable = [];
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

    await axios.get("http://localhost:8080/api/v1/seats/OccupiedSeats")
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
    const { seats } = this.state;
    const selectedSeats = seats.filter(seat => seat.selected)
      .map(seat => {
        const { row, num } = seat;
        return { row, num };
      })
    await selectedSeats.forEach(seat => this.addSeatToDB(seat));
  }


  addSeatToDB = (seat) => {
    try {
      axios.post("http://localhost:8080/api/v1/seats/PostSeats", {
        row: seat.row,
        coloumn: seat.num,
        type: "standard",
        availability: false,
      }, {
        validateStatus: function (status) {
          if (status < 500) alert("seat at row: " + (seat.row + 1) + " coloumn: " + (seat.num + 1) + " booked successfully");
          else alert("double input for seat at row: " + (seat.row + 1) + " coloumn: " + (seat.num + 1) + " error code:" + status);
          return true; // Resolve only if the status code is less than 500
        }
      }).then(this.Unavailable.push([seat.row, seat.num]));
    } catch {
      alert("double input");
    }


    console.log(this.Unavailable);
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


    return (
      <div style={{ backgroundColor: 'black' }}>
        <div><Timer/></div>
        <div className='minibox' style={{ display: showSummary ? 'none' : 'block' }}>
          <div className='movieInfo'>
            <div className='imageContainer'>
              <img src={`https://image.tmdb.org/t/p/original/${movieImage}`} alt="" style={{ height: "200px", width: "400px" }} />
            </div>
            <div align='left'>
              <h1>{title}</h1>
              <p>Run Time: 1 hr 39 mins</p>
              <p>Rating: <img src={PG} alt="" style={{ height: "25px", width: "35px" }} />some violence</p>
            </div>
          </div>
          <div id="stage-container">
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

        <footer class="footer" style={{display: showSummary ? 'none':'block'}}>
            <div className='inputs'>
              <pre>
                Quantity: {chosenSeats.length}
              </pre>
              <pre>
                Cost: ${chosenSeats.length * 8}
              </pre>
              <Button variant="contained" onClick={this.handleCheckout} disabled={chosenSeats.length === 0 ? true:false} style={{ background: 'grey' }}>checkout</Button>
            </div>
        </footer>

        <div className='orderSummary' style={{display: showSummary ? 'block':'none'}}>
        <Timer/>
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
          <Button variant="contained" onClick={this.handleCancel} style={{ background: 'grey' }}>Confirm</Button>
        </div>

      </div>
    );
  }
}

export default SeatApp;
