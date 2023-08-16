import React from "react";
import Navbar from "./Navbar";
<<<<<<< HEAD
import MyCarousel from "./Carousel";
=======
import NavbarLogin from "./NavbarLogin"
>>>>>>> 98ac4c9cb529c8b03802fc75f0899823e9d0efce

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