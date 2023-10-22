import React from 'react';
import MovieTimingSection from './MovieTimingSection';

function MovieTimingList({ locations, timingData, handleButtonClick }) {
  return (
    <div>
      <div className='movieTiming custom-text-color'>
        <div className='loc-section'>
          <strong>Theatre</strong>
        </div>

        <div className='button-section'>
          <strong>Timing</strong>
        </div>
      </div>
      {locations.map((location, index) => (
        <MovieTimingSection
          key={index}
          location={location}
          timings={timingData[index]}
          handleButtonClick={handleButtonClick}
        />
      ))}
    </div>
  );
}

export default MovieTimingList;
