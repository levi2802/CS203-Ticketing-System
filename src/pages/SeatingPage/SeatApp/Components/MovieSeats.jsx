import React from 'react';
import '../SeatApp.css';

function MovieSeats({ rowName, seatsGrid }) {
  return (
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
  );
}

export default MovieSeats;
