import React from "react";
import { Link } from 'react-router-dom'
import "./styles.css"

function Navbar() {
    return (
        <nav>
            <ul className="nav-bar">
<<<<<<< HEAD
                <li className="nav-bar-name"> <Link to="/">TicketWarrior</Link> </li>
                <li> <Link to="/login">Login</Link> </li>
                <li> <Link to="/register">Register</Link> </li>
=======
                <li className="nav-bar-name">TicketWarrior</li>
                <li><a href="#">Login</a></li>
                <li><a href="#">Register</a></li>
>>>>>>> carousel
            </ul>
            <div className="nav-bar-line"></div>
        </nav>
    )
}

export default Navbar