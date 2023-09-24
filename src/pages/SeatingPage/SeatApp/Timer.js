import React, { Component } from 'react';

class Timer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      time: 600, // Initial time in seconds (10 minutes)
    };
  }

  componentDidMount() {
    this.timerInterval = setInterval(() => {
      if (this.state.time > 0) {
        this.setState(prevState => ({ time: prevState.time - 1 }));
      } else {
        // Timer reached 0, navigate to the home page
        window.location.href = '/'; // Redirect to the home page
        clearInterval(this.timerInterval);
      }
    }, 1000);
  }

  componentWillUnmount() {
    clearInterval(this.timerInterval);
  }

  render() {
    const { time } = this.state;
    const minutes = Math.floor(time / 60);
    const seconds = time % 60;

    return (
        
      <div align='center' style={{ fontSize: '30px', fontFamily: 'Montserrat', fontWeight: 'bold'}}>
        <strong>
          {minutes < 10 ? `0${minutes}` : minutes}:{seconds < 10 ? `0${seconds}` : seconds}
        </strong>
      </div>
    );
  }
}

export default Timer;
