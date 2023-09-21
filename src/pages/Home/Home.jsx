import React from "react";
import './Home.css';
import Row from "../Row/Row";
import requests from '../request';
import Banner from "../Banner/Banner";
import NavBar from "../NavBar/NavBar";

function App() {
    return (
        <div className="app">
            <NavBar />
            <Banner />
            <Row title="Trending Now" fetchUrl={requests.fetchTrending} isLargeRow={true} />
            <Row title="Top Rated" fetchUrl={requests.fetchTopRated} />
            <Row title="Action Movies" fetchUrl={requests.fetchActionMovies} />
            <Row title="Comedy Movies" fetchUrl={requests.fetchComedyMovies} />
            <Row title="Horror Movies" fetchUrl={requests.fetchHorrorMovies} />
            <Row title="Romance Movies" fetchUrl={requests.fetchRomanceMovies} />
            <Row title="Documentaries" fetchUrl={requests.fetchDocumentaries} />

        </div>
    );
}

export default App;