import React, {useEffect, useState} from "react";
import SeatApp from "./SeatApp/SeatApp"
import NavbarLogin from "../HomePage/NavbarLogin";
import Navbar from "../HomePage/Navbar";

function SeatPage(){

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
            <SeatApp/>
        </>
    )
}

export default SeatPage;
