import React from 'react';
import '../SeatApp.css';


function MovieInfo({ movieImage, title, location, currentDate, time }) {
  return (
    <div className='movieInfo'>
      <div className='imageContainer'>
        <img src={`https://image.tmdb.org/t/p/original/${movieImage}`} alt="" style={{ height: "200px", width: "auto" }} />
      </div>
      <div align='left'>
        <h1>{title}</h1>
        <p>{location}</p>
        <p>{currentDate}</p>
        <p>{time}</p>
      </div>
    </div>
  );
}

export default MovieInfo;
