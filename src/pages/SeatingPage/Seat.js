import React, { Component } from 'react';
import './Seat.css';

class Seat extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: false,
    };
  }

  handleSeatClick = () => {
    if (this.props.avail && !this.props.reserved) {
      this.setState(prevState => ({
        selected: !prevState.selected,
      }));
      this.props.onSeatSelect(this.props.row, this.props.num);
    }
  }

  render() {
    const { avail, reserved } = this.props;
    const { selected } = this.state;

    let seatClassName = "Unavailable";

    if (reserved) {
      seatClassName = "Unavailable";
    } else if (avail) {
      seatClassName = selected ? "selected" : "Available";
    }

    return (
      <div
        className={seatClassName}
        title={selected ? "Seat Selected" : (avail ? "Seat Available" : "Seat Unavailable")}
        onClick={this.handleSeatClick}
      >
        <center>{this.props.num + 1}</center>
      </div>
    );
  }
}

export default Seat;
