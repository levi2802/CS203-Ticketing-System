import React from "react";
import logo from "./logo.png";
import userProfile from "./user_profile.png";
import './NavBar.css';
import { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";

function NavBar() {

    const [show, handleShow] = useState(false);

    useEffect(() => {
        window.addEventListener("scroll", () => {
            // Scroll is greater than 100 pixels down from the top
            if (window.scrollY > 100) {
                handleShow(true);
            } else {
                handleShow(false);
            }
        });

        return () => {
            window.removeEventListener("scroll");
        };
    }, []);

    return (
        <div className={`nav ${show && "nav_black"}`}>
            <img className="nav_logo" src={logo} alt="Wicket Logo" />
            <img
                className="nav_avatar"
                src={userProfile}
                alt="user logo"
            />
            <Link className="login" to="/login">Login</Link>
            <Link className="register" to="/register">Register</Link>
        </div>
    )
}

export default NavBar;