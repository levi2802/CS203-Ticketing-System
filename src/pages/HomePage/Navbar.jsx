import React from "react";
import "./styles.css"

function Navbar() {
    return (
        <nav>
            <ul className="nav-bar">
                <li className="nav-bar-name">TicketWarrior</li>
                <li><a href="">Login</a></li>
                <li><a href="">Register</a></li>
            </ul>
            <div className="nav-bar-line"></div>
        </nav>
    )
}

export default Navbar