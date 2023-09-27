import React, {useEffect, useState} from "react";
import TimingApp from "./TimingApp/TimingApp";
import NavBar from "../NavBar/NavBar";
import NavBarLoggedIn from "../NavBar/NavBarLoggedIn";

function TimingPage(){
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
            <TimingApp/>
        </>
    )
}

export default TimingPage;