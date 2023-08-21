import React, { Component, useState } from 'react';
import './SeatApp.css';
import Seat from './Seat.js';

class SeatApp extends Component {
  constructor(props){
    super(props);
    this.state = {seats: []};
  }

  componentDidMount(){
    let seats = [
      {row: 0, num: 0, avail: true},{row: 0, num: 1, avail: true},{row: 0, num: 2, avail: true},
      {row: 0, num: 3, avail: true},{row: 0, num: 4, avail: true},{row: 0, num: 5, avail: true},
      {row: 0, num: 6, avail: true},{row: 0, num: 7, avail: true},{row: 0, num: 8, avail: true},
      {row: 0, num: 9, avail: true},

      {row: 1, num: 0, avail: true},{row: 1, num: 1, avail: true},{row: 1, num: 2, avail: true},
      {row: 1, num: 3, avail: true},{row: 1, num: 4, avail: true},{row: 1, num: 5, avail: true},
      {row: 1, num: 6, avail: true},{row: 1, num: 7, avail: true},{row: 1, num: 8, avail: true},
      {row: 1, num: 9, avail: true},

      {row: 2, num: 0, avail: true},{row: 2, num: 1, avail: true},{row: 2, num: 2, avail: true},
      {row: 2, num: 3, avail: true},{row: 2, num: 4, avail: true},{row: 2, num: 5, avail: true},
      {row: 2, num: 6, avail: true},{row: 2, num: 7, avail: true},{row: 2, num: 8, avail: true},
      {row: 2, num: 9, avail: true},
      
      {row: 3, num: 0, avail: true},{row: 3, num: 1, avail: true},{row: 3, num: 2, avail: true},
      {row: 3, num: 3, avail: true},{row: 3, num: 4, avail: true},{row: 3, num: 5, avail: true},
      {row: 3, num: 6, avail: true},{row: 3, num: 7, avail: true},{row: 3, num: 8, avail: true},
      {row: 3, num: 9, avail: true}
    ];

    const seatsToMakeUnavailable = [
      [1, 0],
      [1, 1],
      [0, 0]
      // Add more [row, col] pairs as needed
    ];

   for(let i = 0; i < seats.length;i++){
      for(let x = 0; x < seatsToMakeUnavailable.length;x++){
        let r = seatsToMakeUnavailable[x][0];
        let c = seatsToMakeUnavailable[x][1]
        if(seats[i].row == r && seats[i].num == c ){
          seats[i].avail = false;
        }
      }
   }

    this.setState({seats}, () => console.log(this.state.seats));


    }

    handleSeatSelect = (row, num) => {
      const { seats } = this.state;
      const updatedSeats = seats.map(seat => {
        if (seat.row === row && seat.num === num) {
          return { ...seat, selected: !seat.selected };
        }
        return seat;
      });

      this.setState({
        seats: updatedSeats,
      });
      
      if (updatedSeats.find(seat => seat.row === row && seat.num === num).selected) {
        console.log(`Selected seat: {row: ${row}, column: ${num}}`);
      } else {
        console.log(`Unselected seat: {row: ${row}, column: ${num}}`);
      }
      
    }
  
  render() {
    const { seats } = this.state;
    const seatsGrid = new Array(4).fill(0).map(() => new Array(10).fill(null));

    seats.forEach(seat => {
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
    });

    const selectedSeatNumbers = seats.filter(seat => seat.selected).map(seat => seat.num + 1);
    const availableSeatsCount = seats.filter(seat => seat.avail && !seat.selected).length;
    
    
    return (
      <div>
        <div id="stage-container">
          <svg width="500" height="100" >
          </svg>
          <h1 id="stage">stage</h1>
        </div>
        <div className='center'>
          <div className="Row1">
            {seatsGrid[0].slice(4,10)}
          </div>
          <div className="Row2">
            {seatsGrid[1].slice(4,10)}
          </div>
          <div className="Row3">
            {seatsGrid[2].slice(4,10)}
          </div>
          <div className="Row3">
            {seatsGrid[3].slice(4,10)}
          </div>
        </div>
        <div className='left'>
          <div className="Row1">
            A{seatsGrid[0].slice(0,4)}
          </div>
          <div className="Row2">
            B{seatsGrid[1].slice(0,4)}
          </div>
          <div className="Row3">
            C{seatsGrid[2].slice(0,4)}
          </div>
          <div className="Row3">
            D{seatsGrid[3].slice(0,4)}
          </div>
        </div>
        <div className='inputs'>
          <pre>
          Seats available: {availableSeatsCount}
          </pre>
          <pre>
          Seats Selected: {selectedSeatNumbers.length}
          </pre>
        </div>
        <button>
          submit
        </button>
      </div>
    );
  }
}

export default SeatApp;
