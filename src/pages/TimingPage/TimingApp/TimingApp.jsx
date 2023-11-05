import PG from "../../HomePage/images/PG.png"
import './TimingApp.css';
import { useNavigate } from "react-router-dom";
//import MovieTimingSection from './MovieTimingSection';
import MovieTimingList from "./MovieTimingList";
import Dates from "./Dates";
import React, {useState, useEffect} from "react";



function TimingApp() {
  const movieImage = localStorage.getItem("movieImage");

  // Movie title
  const title = localStorage.getItem("movieTitle");

  const navigate = useNavigate();

  const locations = ['Jurong theatre', 'Hougang theatre', 'Sengkang theatre'];
  const timingDataStr = localStorage.getItem("filteredData");
  const timingData = JSON.parse(timingDataStr);

  const handleButtonClick = (location, time) => {
    localStorage.setItem('selectedLoc', location);
    localStorage.setItem('selectedTime', time);
    navigate('/seating');
  }

  const [selectedDate, setSelectedDate] = useState("");
  const [showMovieTimingList, setShowMovieTimingList] = useState(false);

  useEffect(() => {
    if (selectedDate) {
      setShowMovieTimingList(false);
      setTimeout(() => {
        setShowMovieTimingList(true);
      }, 1);
    }
  }, [selectedDate]);

  return (
    <div>
        <div className='movieInfo'>
            <div className='imageContainer'>
              <img src={`https://image.tmdb.org/t/p/original/${movieImage}`} alt="" style={{ height: "200px", width: "auto" }} />
            </div>
            <div align='left'>
              <h1>{title}</h1>
              <p>Run Time: 1 hr 39 mins</p>
              <p>Rating: <img src={PG} alt="" style={{ height: "25px", width: "35px" }} />some violence</p>
            </div>
        </div>

        <div className="dates">
          <Dates selectedDate={selectedDate} setSelectedDate={setSelectedDate} />
        </div>
        {showMovieTimingList && (
          <MovieTimingList
            locations={locations}
            timingData={timingData}
            handleButtonClick={handleButtonClick}
          />
        )}
    </div>
  );
}

export default TimingApp;
