import React, { useEffect, useState } from "react";
import axios from "../axios";
import requests from "../request";
import './Banner.css';
import YouTube from "react-youtube";
import movieTrailer from "movie-trailer";
import { useNavigate } from "react-router-dom";

function Banner() {
    const [movie, setMovie] = useState(null);

    const [trailerUrl, setTrailerUrl] = useState("");

    useEffect(() => {
        async function fetchData() {
            const request = await axios.get(requests.fetchTrending)
            setMovie(request.data.results[Math.floor(Math.random() * request.data.results.length - 1)]);
            return request;
        }
        fetchData();
    }, []);

    localStorage.setItem("movieImage", movie?.poster_path);
    localStorage.setItem("movieTitle", movie?.title || movie?.name || movie?.original_name);

    function truncate(str, n) {
        return str?.length > n ? str.substr(0, n - 1) + "..." : str;
    }

    const opts = {
        height: "390",
        width: "700px",
        playerVars: {
            // https://developers.google.com/youtube/player_parameters for more information
            autoplay: 1,
        },
    };

    const handleClick = (movie) => {
        if (trailerUrl) {
            // when video is playing set trailer url to empty again
            setTrailerUrl('');
        } else {
            movieTrailer(movie?.name || movie?.title || "")
                .then((url) => {
                    const urlParams = new URLSearchParams(new URL(url).search);
                    setTrailerUrl(urlParams.get("v"));
                })
                .catch((error) => {
                    console.log(error);
                })
        }
    }

    // Navigate to the seating page on button click
    const navigate = useNavigate();

    const handleButtonClick = () => {
        navigate('/Timing');
    }

    return (
        <header className="banner" style={{
            backgroundSize: 'cover',
            backgroundImage: `url("https://image.tmdb.org/t/p/original/${movie?.backdrop_path}")`,
            backgroundPosition: 'center center'
        }}>

            <div className="banner_contents">
                <h1 className="banner_title">{movie?.title || movie?.name || movie?.original_name}</h1>

                <div className="banner_buttons">
                    <button className="banner_button" onClick={() => handleClick(movie)}>Play Trailer</button>
                    <button className="banner_button" onClick={() => handleButtonClick()}>Buy Tickets</button>
                </div>

                <h1 className="banner_description">{truncate(movie?.overview, 150)}</h1>
            </div>

            {trailerUrl && <YouTube className="youtube_container" videoId={trailerUrl} opts={opts} />}

            <div className="banner--fadeBottom" />
        </header>
    )
}

export default Banner;