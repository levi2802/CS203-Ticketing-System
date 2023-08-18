import React, { Component } from 'react';
import './Seat.css';

class Seat extends Component {
	constructor(props){
		super(props)
		this.state = {selected: false}
		this.selectSeat = this.selectSeat.bind(this);
	}

	selectSeat(){
		console.log(`seat: {row: ${this.props.row}, num: ${this.props.num}}`)
		this.setState({selected: this.state.selected? false: true });
	}

	render() {
		let seat = <div className="Unavailable" title="Seat Unavailable"></div>

		if (this.props.avail) {
			seat = <div className="Available" title={this.state.selected? "Seat Selected" : "Seat Available \n$50\n" + ( 1 + this.props.num)} id={this.state.selected? "selected" : ""} onClick={this.selectSeat.bind(this)}></div>
		} 

		return(
			<div>
				{seat}
			</div>
		)
	}
}

export default Seat;