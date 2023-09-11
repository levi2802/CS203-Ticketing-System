import React from "react";
import Navbar from "./Navbar";
import MyCarousel from "./Carousel";
import { Link } from 'react-router-dom'

function Home() {
    return (
        <>
            <Navbar />
            <MyCarousel />
            <button className="buy-btn"><Link to="/seating">Buy Tickets</Link></button>
        </>
    )
}

export default Home;