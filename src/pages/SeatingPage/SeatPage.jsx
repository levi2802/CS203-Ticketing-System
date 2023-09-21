import React, {useEffect, useState} from "react";
import SeatApp from "./SeatApp/SeatApp"
import NavBar from "../NavBar/NavBar";
import NavBarLoggedIn from "../NavBar/NavBarLoggedIn";

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
            {hasToken ? <NavBarLoggedIn/> : <NavBar/>}
            <SeatApp/>
        </>
    )
}

export default SeatPage;
