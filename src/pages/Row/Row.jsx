import React, { useState, useEffect } from "react";
import axios from '../axios';
import './Row.css';
import YouTube from "react-youtube";
import movieTrailer from "movie-trailer";
import { useNavigate } from "react-router-dom";

const base_url = "http://image.tmdb.org/t/p/original/";

function Row({ title, fetchUrl, isLargeRow }) {
    const [movies, setMovies] = useState([]);

    const [trailerUrl, setTrailerUrl] = useState("");

    // Snippet of code which runs based on a specific condition/variable
    useEffect(() => {
        // if [], run once when row loads, and don't run again.
        // Any variable that is outside of use effect that is used inside use effect, you have to include in []. Why? Dependent on the variable []
        async function fetchData() {
            const request = await axios.get(fetchUrl);
            setMovies(request.data.results);
            return request;
        }
        fetchData();
    }, [fetchUrl]);

    const opts = {
        height: "390",
        width: "100%",
        playerVars: {
            // https://developers.google.com/youtube/player_parameters for more information
            autoplay: 1,
        },
    };

    const someMovies = movies.slice(0, 4);

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

    const setMovieData = (movie) => {
        localStorage.setItem("movieImage", movie?.poster_path);
        localStorage.setItem("movieTitle", movie?.title || movie?.name || movie?.original_name);
    }

    const navigate = useNavigate();

    const handleButtonClick = (movie) => {
        // Saving movie data to local storage
        setMovieData(movie);

        // Navigate to the seating page on button click
        navigate('/Timing');
    }

    return (
        <div className="row">
            <h2>{title}</h2>
            <div className="new-page-border"></div>
            <div className="row_posters">
                {/*several row_poster(s)*/}
                {someMovies.map(movie => (
                    <div
                        key={movie.id}
                        className={`row_posterInnerWrapper ${isLargeRow && "row_posterLarge"}`}
                        onClick={() => handleClick(movie)}
                    >
                        <img
                            className="row_poster"
                            src={`${base_url}${isLargeRow ? movie.poster_path : movie.backdrop_path}`}
                            alt={movie.name}
                        />

                        <h3 className="movie-title">{`${movie?.title || movie?.name || movie?.original_name}`}</h3>

                        <button className="buyTicketsButton" onClick={() => handleButtonClick(movie)}>Buy Tickets</button>
                    </div>
                ))}

            </div>

            { trailerUrl && <YouTube videoId={trailerUrl} opts={opts} /> }
        </div >
    )
}

export default Row;