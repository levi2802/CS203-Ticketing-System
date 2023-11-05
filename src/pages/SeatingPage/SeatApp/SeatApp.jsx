import React, { Component } from 'react';
import './SeatApp.css';
import Seat from '../Seat/Seat.js';
import axios from 'axios';
import Button from '@mui/material/Button'
import * as util from './SeatUtils';
import OrderSummary from './Components/OrderSummary';
import MovieLegend from './Components/MovieLegend';
import Stage from './Components/stage';
import MovieSeats from './Components/MovieSeats';
import MovieInfo from './Components/MovieInfo';


class SeatApp extends Component {
  template = function (row, column, type, availability) { }
  //switch them based on which database you are testing on
  backendURL = "http://localhost:8080"
  //backendURL= "http://api.wicket.shop:8080"
  Unavailable = [];
  d = new Date();

  movieName = localStorage.getItem("movieTitle");
  location = localStorage.getItem("selectedLoc");
  timing = localStorage.getItem("selectedTime");
  currentDate = localStorage.getItem('selectedDate');

  dateConvertor(currDate) {
    const date = new Date(currDate);
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const formattedDate = date.toLocaleString('en-GB', options);
    return formattedDate;
  }

  constructor(props) {
    super(props);
    this.state = { seats: [], chosenSeats: [], showSummary: false };
  }

  async fetchSeats() {
    let seats = [];

    // Store the currently selected seats in a temporary array
    const currentlySelectedSeats = this.state.seats.filter(seat => seat.selected);

    // How many seats to create r=row i=col
    for (let r = 0; r < 4; r++) {
      for (let i = 0; i < 14; i++) {
        seats.push({ row: r, num: i, avail: true });
      }
    }

    try {
      // const date = new Date(this.currentDate);
      // const options = { year: 'numeric', month: 'long', day: 'numeric' };
      // const formattedDate = date.toLocaleString('en-GB', options);

      const response = await axios.get(this.backendURL + "/api/v1/seats/" + this.movieName + "/" + this.location + "/" + this.timing + "/" + this.dateConvertor(this.currentDate));
      response.data.forEach(data => this.Unavailable.push([data.row, data.column]));

      console.log(this.Unavailable);

      // Code to make seat unavail
      util.makeUnavail(seats, this.Unavailable);

      // Reapply the selected seats to the updated seats array
      currentlySelectedSeats.forEach(selectedSeat => {
        const seatToUpdate = seats.find(seat => seat.row === selectedSeat.row && seat.num === selectedSeat.num);
        if (seatToUpdate && seatToUpdate.avail) {
          seatToUpdate.selected = true;
        }
      });

      // Updating
      this.setState({ seats });
    } catch (error) {
      console.error(error);
    }
  }


  async componentDidMount() {
    // Fetch once initially.
    await this.fetchSeats();

    this.eventSource = new EventSource('http://localhost:3001/sse');
    this.eventSource.onmessage = (event) => {
      console.log('Database change detected:', JSON.parse(event.data));
      this.fetchSeats(); // Fetch seats whenever a change is detected
    };
  }

  componentWillUnmount() {
    this.eventSource.close();
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
    this.setState({ showSummary: true });
  };

  handleCancel = () => {
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

    // const headers = {
    //   'Authorization': `Bearer ${accessToken}`
    // };

    const { seats } = this.state;
    const selectedSeats = seats.filter(seat => seat.selected)
      .map(seat => {
        const { row, num } = seat;
        return { row, num };
      })

    //adding purchased seats to backend
    await selectedSeats.forEach(seat => this.addSeatToDB(seat, username));
    const rowName = [];
    for (let i = 0; i < seats.length; i++) {
      let temp = 'A'.charCodeAt(0) + i;
      rowName.push(String.fromCharCode(temp));
    }

    let selectedSeatsStringArray = selectedSeats.map(seat => {
      seat.num += 1;  // Increment seat.num by 1
      return rowName[seat.row] + seat.num; // directly return the seatID for each iteration
    });

    // const date = new Date(this.currentDate);
    // const options = { year: 'numeric', month: 'long', day: 'numeric' };
    // const formattedDate = date.toLocaleString('en-GB', options);

    const selectedSeatsString = selectedSeatsStringArray.join(", ").replace(/,\s*$/, "");  // join and then remove the trailing comma (if any)
    let alertMessage = "Your seats: " + selectedSeatsString + " for the movie: " + this.movieName + " at " + this.location + " on " + this.dateConvertor(this.currentDate) + " at " + this.timing + " are booked!";
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
    // const date = new Date(this.currentDate);
    // const options = { year: 'numeric', month: 'long', day: 'numeric' };
    // const formattedDate = date.toLocaleString('en-GB', options);

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
        timing: this.timing,
        movieDate: this.dateConvertor(this.currentDate)
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
        price: selectedSeats.length * 8,
        movieDate: this.dateConvertor(this.currentDate)
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
    const currentDate = localStorage.getItem("selectedDate");


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
