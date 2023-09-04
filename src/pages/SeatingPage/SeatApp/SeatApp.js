import React, { Component, useState } from 'react';
import './SeatApp.css';
import Seat from '../Seat/Seat.js';
import { useRef } from 'react';

class SeatApp extends Component {
  constructor(props){
    super(props);
    this.state = {seats: [], chosenSeats: [], showSummary: false};
  }

  componentDidMount(){
    let seats = [];

    //How many seats to create r=row i=col
    for(let r = 0; r < 4; r++){
      for(let i = 0; i < 14; i++){
        seats.push({row: r, num: i,avail:true});
      }
    }

    //example
    const makeUnavailable = [[1, 0],[1, 1],[0, 0]];

    //code to make seat unavail r = row c = col
    for(let i = 0; i < seats.length;i++){
        for(let j = 0; j < makeUnavailable.length; j++){
          let r = makeUnavailable[j][0];
          let c = makeUnavailable[j][1]
          if(seats[i].row === r && seats[i].num === c ){
            seats[i].avail = false;
          }
        }
    }

    //Updating
    this.setState({seats}, () => console.log(this.state.seats));
    }

    handleSeatSelect = (row, num) => {
      const {seats} = this.state;
      //object of the seat that i clicked
      const selectedSeat = seats.find(seat => seat.row === row && seat.num === num);
    
      //A list of selected seat numbers
      const selectedSeatNumbers = [];
      seats.forEach(seat => {
        if (seat.row === row && seat.selected) {
          selectedSeatNumbers.push(seat.num);
        }
      });
    
      //Seat picking algo
      const updatedSeats = seats.map(seat => {
        if (seat.row === row && seat.num === num) {
          if (selectedSeatNumbers.includes(num)) {
            // If clicked seat is in selected group
            if (selectedSeatNumbers.length > 1) {
              // Check if it's the maximum or minimum seat number in the group
              let maxSelected = Math.max(...selectedSeatNumbers);
              let minSelected = Math.min(...selectedSeatNumbers);
              //Only allow the outsides to be 'de-selected'
              if (num === maxSelected || num === minSelected) {
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
            selectedSeatNumbers.length === 0 || selectedSeatNumbers.includes(num - 1) ||
            selectedSeatNumbers.includes(num + 1) || selectedSeatNumbers.includes(num)
          ) {
            seat.selected = !seat.selected;
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
        console.log(`Selected seat: {row: ${row}, column: ${num}}`);
      } 
      else {
        console.log(`Unselected seat: {row: ${row}, column: ${num}}`);
      }
    }

    handleCheckout = () => {
    const {chosenSeats} = this.state;
    if (chosenSeats.length === 0) {
      alert("Please select at least one seat before checkout");
    } 
    else {
      this.setState({showSummary: true});
      }
    };

    handleCancel = () => {
      const {chosenSeats} = this.state;
      this.setState({showSummary: false});
    }

    //Handling confirm button to give info to backend
    handleConfirm = () => {
      const {seats} = this.state;
      const selectedSeats = seats.filter(seat => seat.selected)
      .map(seat => {
        const {row, num} = seat;
        return {row, num};Y
      })
      console.log(selectedSeats);
    }

  
  
  render() {
    const {seats, showSummary} = this.state;

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
    for(let i = 0; i < seatsGrid.length; i++){
      let temp = 'A'.charCodeAt(0) + i;
      rowName.push(String.fromCharCode(temp));
    }

    const chosenSeats = seats.filter(seat => seat.selected).map(seat => seat.num + 1);
    const chosenRow = seats.filter(seat => seat.selected).map(seat => seat.row + 1);
    const availableSeats = seats.filter(seat => seat.avail && !seat.selected).length;
    
    return (
      <div>
        <div className='lock' style={{display: showSummary ? 'none':'block'}}>
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

          <div className='inputs'>
            <pre>
              <div id = 'Unavailable' style={{ display: 'inline-block'}}></div>Unavailable
              <div id = 'AvailableLegend' style={{ display: 'inline-block', marginLeft: '10px'}}></div>Available
              <div id = 'selected' style={{ display: 'inline-block', marginLeft: '10px'}}></div>Selected
            </pre>
            <pre>
              Seats available: {availableSeats}/{seatsGrid.length * seatsGrid[0].length}
            </pre>
            <pre>
              Seats Selected: {chosenSeats.length}
            </pre>
            <button onClick={this.handleCheckout}>
              Check Out
            </button>
          </div>
        </div>

        <div className='orderSummary' style={{display: showSummary ? 'block':'none'}}>
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
