import React from 'react';

function MovieTimingSection({ location, timings, handleButtonClick }) {
  return (
    <div className='movieTiming'>
      <div className='loc-section'>
        <strong>{location}</strong>
      </div>

      <div className='button-section'>
        {timings.map((time, index) => (
          <button className='white-button' key={index} onClick={() => handleButtonClick(location, time)}>
            {time}
          </button>
        ))}
      </div>
    </div>
  );
}

export default MovieTimingSection;