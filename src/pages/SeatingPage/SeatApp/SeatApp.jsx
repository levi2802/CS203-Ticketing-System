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
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';


class SeatApp extends Component {
  template = function (row, column, type, availability) { }
  //switch them based on which database you are testing on
  backendURL = "http://localhost:8080"
  // backendURL= "http://13.212.113.161:8080"
  Unavailable = [];
  d = new Date();

  movieName = localStorage.getItem("movieTitle");
  location = localStorage.getItem("selectedLoc");
  timing = localStorage.getItem("selectedTime");

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

    await axios.get(this.backendURL + "/api/v1/seats/" + this.movieName + "/" + this.location + "/" + this.timing)
      .then(json => json.data.forEach(data => this.Unavailable.push([data.row, data.column])))
      .catch(console.error);

    console.log(this.Unavailable);

    //code to make seat unavail
    util.makeUnavail(seats, this.Unavailable);

    //Updating
    this.setState({ seats });

    // WebSocket logic
    const client = new Client({
      brokerURL: this.backendURL + '/ws',
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log('Connected to WebSocket!');

        client.subscribe('/topic/seats', (message) => {
          if (message.body) {
            // Handle seat changes here (e.g., reload the seat data)
            console.log("Received seat update:", message.body);
            this.refreshSeatData(); // you might want to create this method to refetch seat data
          }
        });
      }
    });

    client.webSocketFactory = () => new SockJS(this.backendURL + '/ws');
    client.activate();
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
    const selectedSeats = seats.filter(seat => seat.selected)
      .map(seat => {
        const { row, num } = seat;
        return { row, num };
      })

    //adding purchased seats to backend
    await selectedSeats.forEach(seat => this.addSeatToDB(seat, username));
    let seatIDs = [];
    const rowName = [];
    for (let i = 0; i < seats.length; i++) {
      let temp = 'A'.charCodeAt(0) + i;
      rowName.push(String.fromCharCode(temp));
    }

    let selectedSeatsStringArray = selectedSeats.map(seat => {
      seat.num += 1;  // Increment seat.num by 1
      return rowName[seat.row] + seat.num; // directly return the seatID for each iteration
    });

    const selectedSeatsString = selectedSeatsStringArray.join(", ").replace(/,\s*$/, "");  // join and then remove the trailing comma (if any)
    const alertMessage = "Your seats: " + selectedSeatsString + " for the movie: " + this.movieName + " at " + this.location + " " + this.timing + " are booked!";
    alert(alertMessage);
    console.log(selectedSeatsStringArray);


    // Create confirmation email.
    const sendEmail = async () => {
      try {
        const response = await axios.get(`${this.backendURL}/api/v1/mail/${username}/${alertMessage}`);
        console.log("Email sent successfully!", response.data);
      } catch (error) {
        console.error("There is an error", error);
      }
    }

    // Save purchase order to the database.
    await this.addPurchaseOrder(username, selectedSeatsString, selectedSeats);
    window.location.href = '/';
    // Send confirmation email.
    await sendEmail()
  }

  addSeatToDB = (seat, username) => {
    try {
      const accessToken = localStorage.getItem('accessToken');
      const headers = {
        'Authorization': `Bearer ${accessToken}`
      };
      axios.post(this.backendURL + "/api/v1/seats", {
        row: seat.row,
        column: seat.num,
        type: "standard",
        availability: false,
        username: username,
        movieName: this.movieName,
        location: this.location,
        timing: this.timing
      }, {
        headers: headers
      }).then(this.Unavailable.push([seat.row, seat.num]));
    } catch {
      alert("how did you get here? please report steps done to team(addseat threw exception)");
    }


    console.log(this.Unavailable);
  }

  addPurchaseOrder = (username, seatIDs, selectedSeats) => {
    console.log(seatIDs);
    try {
      const accessToken = localStorage.getItem('accessToken');
      const headers = {
        'Authorization': `Bearer ${accessToken}`
      };
      axios.post(this.backendURL + "/api/v1/purchases", {
        userId: username,
        movieId: this.movieName,
        seatIDs: seatIDs,
        location: this.location,
        timing: this.timing,
        price: selectedSeats.length * 8
      }, { headers: headers })
        .then();
    } catch {
      alert("how did you get here? please report steps done to team (postpurchase threw exception)");
    }
    console.log("purchase add success")
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
          <Stage />
          <MovieSeats rowName={rowName} seatsGrid={seatsGrid} />
          <MovieLegend />
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
