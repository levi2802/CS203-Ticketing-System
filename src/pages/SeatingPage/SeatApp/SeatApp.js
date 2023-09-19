import React, { Component } from 'react';
import './SeatApp.css';
import Seat from '../Seat/Seat.js';
import axios from 'axios';
import sample1 from "../../HomePage/images/image1.png";
import PG from "../../HomePage/images/PG.png"
import Button from '@mui/material/Button'
import Timer from './Timer';

class SeatApp extends Component {
  constructor(props) {
    super(props);
    this.state = { seats: this.initializeSeats(4, 14), chosenSeats: [], showSummary: false, unavailable: [] };
  }

  initializeSeats(rows, cols) {
    return Array.from({ length: rows }).flatMap((_, row) =>
      Array.from({ length: cols }).map((_, col) => ({ row, num: col, avail: true, selected: false }))
    );
  }

  async componentDidMount() {
    try {
      const response = await axios.get("/movies/{movieId}/occupiedSeats");
      const unavailable = response.data.map(data => `row: ${data.row}, column: ${data.column}`);
      const seats = this.state.seats.map(seat => ({
        ...seat,
        avail: !unavailable.includes(`row: ${seat.row + 1}, column: ${seat.num + 1}`)
      }));
      this.setState({ seats, unavailable });
    } catch (error) {
      console.error(error);
    }
  }

  handleSeatSelect = (row, num) => {
    let { seats } = this.state;
    let selectedSeatNumbers = seats.filter(seat => seat.row === row && seat.selected).map(seat => seat.num);
  
    let maxSelected = Math.max(...selectedSeatNumbers, -1);
    let minSelected = Math.min(...selectedSeatNumbers, 15);
  
    seats = seats.map(seat => {
      if (seat.row === row && seat.num === num) {
        if (seat.avail) {
          if (!seat.selected || num === maxSelected || num === minSelected) {
            seat.selected = !seat.selected;
          } else {
            alert("Please choose seats without a space between.");
          }
        } else {
          alert("This seat is already occupied.");
        }
      }
      return seat;
    });
  
    this.setState({
      seats,
      chosenSeats: seats.filter(seat => seat.selected).map(seat => seat.num + 1)
    });
  }

  handleCheckout = () => this.setState({ showSummary: true });

  handleCancel = () => this.setState({ showSummary: false });

  handleConfirm = async () => {
    const selectedSeats = this.state.seats.filter(seat => seat.selected);
    const selectedSeatStrings = selectedSeats.map(seat => `row: ${seat.row + 1}, column: ${seat.num + 1}`);
    try {
      await this.createOrder(selectedSeatStrings);
    } catch (error) {
      console.error('Error creating order: ', error);
    }
  }

  createOrder = async (selectedSeatStrings) => {
    const orderData = {
      user: { id: this.state.userId },  // Assuming you have userId in state
      movie: { id: this.state.movieId },  // Assuming you have movieId in state
      seats: selectedSeatStrings,
      purchaseDateTime: new Date().toISOString(),
      totalPrice: this.state.totalPrice,  // Assuming you have totalPrice in state
    };

    try {
      const response = await axios.post("http://localhost:8080/users/{userId}/orders", orderData);
      console.log('Order created successfully: ', response);
      // Navigate to a confirmation page or show a success message
    } catch (error) {
      console.error('Error creating order: ', error);
      // Show an error message to the user
    }
  }


  renderSeatsGrid = (startIndex, endIndex) => {
    const seatsGrid = this.state.seats.reduce((acc, seat) => {
      acc[seat.row] = [...(acc[seat.row] || []), (
        <Seat key={`${seat.row}-${seat.num}`} {...seat} onSeatSelect={this.handleSeatSelect} />
      )];
      return acc;
    }, []);
    return seatsGrid.map((row, index) => (
      <div className="Row" key={index}>
        {String.fromCharCode('A'.charCodeAt(0) + index)}
        {row.slice(startIndex, endIndex)}
      </div>
    ));
  }

  render() {
    const { seats, showSummary, chosenSeats } = this.state;
    const rowName = [...Array(4)].map((_, i) => String.fromCharCode('A'.charCodeAt(0) + i));
    const chosenRow = seats.filter(seat => seat.selected).map(seat => seat.row + 1);

    return (
      <div>
        <Timer />
        <div className='minibox' style={{ display: showSummary ? 'none' : 'block' }}>
          <div className='movieInfo'>
            <div className='imageContainer'>
              <img src={sample1} alt="" style={{ height: "200px", width: "400px" }} />
            </div>
            <div align='left'>
              <h1>Teenage Mutant Ninja Turtles: Mutant Mayhem (忍者龟：变种大乱斗)</h1>
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

        <footer class="footer">
          <div className='inputs'>
            <pre>
              Quantity: {chosenSeats.length}
            </pre>
            <pre>
              Cost: ${chosenSeats.length * 8}
            </pre>
            <Button variant="contained" onClick={this.handleCheckout} disabled={chosenSeats.length === 0 ? true : false}>checkout</Button>
          </div>
        </footer>

        <div className='orderSummary' style={{ display: showSummary ? 'block' : 'none' }}>
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
          <button onClick={this.handleCancel}>Cancel</button>
          <button onClick={this.handleConfirm}>Confirm</button>
        </div>

      </div>
    );
  }
}

export default SeatApp;
