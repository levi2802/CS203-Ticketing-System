import React, { Component, useState } from 'react';
import './SeatApp.css';
import Seat from '../Seat/Seat.js';

class SeatApp extends Component {
  constructor(props){
    super(props);
    this.state = {seats: []};
  }

  componentDidMount(){
    let seats = [];

    //How many seats to create r=row i = col
    for(let r = 0; r < 4; r++){
      for(let i = 0;i < 14;i++){
        seats.push({row: r, num: i,avail:true});
      }
    }

    const seatsToMakeUnavailable = [[1, 0],[1, 1],[0, 0]];

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
      const {seats} = this.state;
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
    const {seats} = this.state;
    const seatsGrid = new Array(4).fill(0).map(() => new Array(14).fill(null));

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
          <h1 id="stage">Screen</h1>
        </div>
        <div className='layout'>
        <div className='left'>
          {['A', 'B', 'C', 'D'].map((rowLabel, rowIndex) => (
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
        <div className='inputs'>
          <pre>
          Seats available: {availableSeatsCount}/{seatsGrid.length * seatsGrid[0].length}
          </pre>
          <pre>
          Seats Selected: {selectedSeatNumbers.length}
          </pre>
          <button>
          submit
          </button>
        </div>
      </div>
    );
  }
}

export default SeatApp;
