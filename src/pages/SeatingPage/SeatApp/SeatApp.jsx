import React, { Component, useState } from 'react';
import './SeatApp.css';
import Seat from '../Seat/Seat.js';
import axios, { formToJSON } from 'axios';
import { json, useParams } from 'react-router-dom';
import Button from '@mui/material/Button'
import Timer from './Timer';
import { useNavigate } from "react-router-dom";
import { recomposeColor } from '@mui/material';
import * as util from './SeatUtils';
import OrderSummary from './Components/OrderSummary';
import MovieLegend from './Components/MovieLegend';
import Stage from './Components/stage';
import MovieSeats from './Components/MovieSeats';
import MovieInfo from './Components/MovieInfo';


class SeatApp extends Component {
  template = function (row, coloumn, type, availability) { }
  Unavailable = [];
  d = new Date();
  constructor(props) {
    super(props);
    this.state = { seats: [], chosenSeats: [], showSummary: false };
  }


  async componentDidMount() {
    let Row = 4;
    let Col = 14;
    let seats = util.generateSeats(Row, Col);

    const moviename = localStorage.getItem("movieTitle");
    console.log(moviename);
    await axios.get("http://localhost:8080/api/v1/seats/OccupiedSeats/" + moviename)
      .then(json => json.data.forEach(data => this.Unavailable.push([data.row, data.coloumn])))
      .catch(console.error);

    //console.log(this.Unavailable);

    //code to make seat unavail
    util.makeUnavail(seats, this.Unavailable);

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
    const updatedSeats = util.handleSeatSelect(seats, row, col);

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
    let seatIDs = [];
    const rowName = [];
    for (let i = 0; i < seats.length; i++) {
      let temp = 'A'.charCodeAt(0) + i;
      rowName.push(String.fromCharCode(temp));
    }
    window.location.href = '/';
    let selectedSeatsString = "";
    selectedSeats.forEach(seat => {
      seat.num += 1;  // Increment seat.num by 1
      let seatID = rowName[seat.row] + seat.num;
      selectedSeatsString = selectedSeatsString + seatID + ', ';
      seatIDs.push(seatID);
    })
    const alertMessage = "Your seats: " + selectedSeatsString + "for the movie: " + movieName + " are booked!";
    alert(alertMessage);
    console.log(seatIDs);
    await this.addPurchaseOrder(username, seatIDs, movieName);
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
    console.log(seatIDs);
    try {
      const accessToken = localStorage.getItem('accessToken');
      const headers = {
        'Authorization': `Bearer ${accessToken}`
      };
      axios.post("http://localhost:8080/api/purchases/postPurchase", {
        userId: username,
        movieId: movieName,
        seatIDs: seatIDs,
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
    let Row = 4;
    let Col = 14;
    const seatsGrid = util.nullArray(Row, Col);

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
    const rowName = util.charConverter(seatsGrid);

    const chosenSeats = seats.filter(seat => seat.selected).map(seat => seat.num + 1);
    const chosenRow = seats.filter(seat => seat.selected).map(seat => seat.row + 1);
    const location = localStorage.getItem("selectedLoc");
    const time = localStorage.getItem("selectedTime");
    const currentDate = new Date().toLocaleDateString();


    return (
      <div style={{ backgroundColor: 'black' }}>
        <div className='minibox' style={{ display: showSummary ? 'none' : 'block' }}>
        <MovieInfo
          movieImage={movieImage}
          title={title}
          location={location}
          currentDate={currentDate}
          time={time}
        />
          <Stage/>
          <MovieSeats rowName={rowName} seatsGrid={seatsGrid} />
          <MovieLegend/>
        </div>

        <OrderSummary
          chosenSeats={chosenSeats}
          rowName={rowName}
          chosenRow={chosenRow}
          handleCancel={this.handleCancel}
          handleConfirm={this.handleConfirm}
          showSummary={showSummary}
        />

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
      </div>
    );
  }
}

export default SeatApp;
