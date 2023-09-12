import React, {useEffect, useState} from "react";
import Navbar from "./Navbar";
import MyCarousel from "./Carousel";
import NavbarLogin from "./NavbarLogin";

function Home() {

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
        <>
            {hasToken ? <NavbarLogin/> : <Navbar/>}
            <MyCarousel />
            <button className="buy-btn">Buy Ticket</button>
        </>
    )
}

export default Home;