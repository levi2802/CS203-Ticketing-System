import React from "react";
import Navbar from "./Navbar";
import MyCarousel from "./Carousel";

function Home() {
    return (
        <>
            <Navbar />
            <MyCarousel />
            <button className="buy-btn">Buy Ticket</button>
        </>
    )
}

export default Home;