import React, {useEffect, useState} from "react";
import './Home.css';
import Row from "../Row/Row";
import requests from '../request';
import Banner from "../Banner/Banner";
import NavBar from "../NavBar/NavBar";
import NavBarLoggedIn from "../NavBar/NavBarLoggedIn";

function App() {

    const [hasToken, setHasToken] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('accessToken');

        if (token) {
            setHasToken(true);
        } else {
            setHasToken(false);
        }
    }, []);

    return (
        <div className="app">
            {hasToken ? <NavBarLoggedIn/> : <NavBar/>}
            <Banner />
            <Row title="Now Showing" fetchUrl={requests.fetchTrending} isLargeRow={true} />
            <Row title="Past Favourites" fetchUrl={requests.fetchTopRated} />
            <Row title="Advance Sales" fetchUrl={requests.fetchActionMovies} />
            <Row title="Comedy Movies" fetchUrl={requests.fetchComedyMovies} />
            <Row title="Horror Movies" fetchUrl={requests.fetchHorrorMovies} />
            <Row title="Romance Movies" fetchUrl={requests.fetchRomanceMovies} />
            <Row title="Documentaries" fetchUrl={requests.fetchDocumentaries} />

        </div>
    );
}

export default App;