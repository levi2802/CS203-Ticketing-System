import React, { Component, useState } from 'react';
import './SeatApp.css';
import Seat from './Seat.js';

class SeatApp extends Component {
  constructor(props){
    super(props);
    this.state = {seats: []};
  }

  componentDidMount(){
    this.setState({seats: [
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
    ]}, () => console.log(this.state.seats));
  }
  
  render() {
    let seats = new Array(4);
    for(let i = 0; i < seats.length; i++ ){
      seats[i] = new Array(10);
    }

    if (this.state.seats) {
      this.state.seats.forEach(seat => {
        seats[seat.row][seat.num] = <Seat row = {seat.row} num = {seat.num}
        avail = {seat.avail} key = {seat.row + seat.num} />
      });
    } 

    //Collate those that are selected and make it false
    //needs to be an array of arrays
    let x = [[0,1],[1,1],[1,2]];
    //get from backend data on what seats are purchased
    for(let i = 0; i < x.length; i++){
      let r = x[i][0];
      let c = x[i][1];
      if (this.state.seats) {
        this.state.seats.forEach(seat => {
          seats[r][c] = <Seat row = {r} num = {c}
          avail = {false} key = {r + c} />
        });
      } 
    }
    
    
    return (
      <div>
        <div id="stage-container">
          <svg width="500" height="200">
            <ellipse cx="250" cy="100" rx="250" ry="100" />
            <rect x="0" y="0" width="500" height="100" />
          </svg>
          <h1 id="stage">stage</h1>
        </div>
        <div className='center'>
          <div className="Row1">
            {seats[0].slice(4,10)}
          </div>
          <div className="Row2">
            {seats[1].slice(4,10)}
          </div>
          <div className="Row3">
            {seats[2].slice(4,10)}
          </div>
          <div className="Row3">
            {seats[3].slice(4,10)}
          </div>
        </div>
        <div className='left'>
          <div className="Row1">
            A{seats[0].slice(0,4)}
          </div>
          <div className="Row2">
            B{seats[1].slice(0,4)}
          </div>
          <div className="Row3">
            C{seats[2].slice(0,4)}
          </div>
          <div className="Row3">
            D{seats[3].slice(0,4)}
          </div>
        </div>
      </div>
    );
  }
}

export default SeatApp;