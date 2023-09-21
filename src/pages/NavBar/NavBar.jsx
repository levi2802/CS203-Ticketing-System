import React from "react";
import logo from "./logo.png";
import userProfile from "./user_profile.png";
import './NavBar.css';
import { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";

function NavBar() {

    const [show, handleShow] = useState(false);

    const handleScroll = () => {
        // Scroll is greater than 100 pixels down from the top
        if (window.scrollY > 100) {
            handleShow(true);
        } else {
            handleShow(false);
        }
    };

    useEffect(() => {
        window.addEventListener("scroll", handleScroll);

        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, []);

    return (
        <div className={`nav ${show && "nav_black"}`}>
            <Link to="/"><img className="nav_logo" src={logo} alt="Wicket Logo" /></Link>
            <img
                className="nav_avatar"
                src={userProfile}
                alt="user logo"
            />
            <Link className="loginNavbar" to="/login">Login</Link>
            <Link className="registerNavbar" to="/register">Register</Link>
        </div>
    )
}

export default NavBar;