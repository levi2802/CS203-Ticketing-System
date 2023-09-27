import PG from "../../HomePage/images/PG.png"
import './TimingApp.css';
import { useNavigate } from "react-router-dom";
import MovieTimingSection from './MovieTimingSection';



function TimingApp() {
  const movieImage = localStorage.getItem("movieImage");

  // Movie title
  const title = localStorage.getItem("movieTitle");

  const navigate = useNavigate();

  const locations = ['Jurong theatre', 'Hougang theatre', 'Sengkang theatre'];
  const timingData = [
    ['09:00 AM', '11:15 AM'],
    ['09:00 AM'],
    ['09:00 AM', '12:30 PM', '02:45 PM'],
  ];

  const handleButtonClick = (location, time) => {
    localStorage.setItem('selectedLoc', location);
    localStorage.setItem('selectedTime', time);
    navigate('/seating');
}

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

export default TimingApp;