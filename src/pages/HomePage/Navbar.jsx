import React, { useState, useEffect } from "react";
import { Link } from 'react-router-dom'
import "./styles.css"

function Navbar() {

    return (
        <nav>
            <ul className="nav-bar">
                <li className="nav-bar-name"><Link to="/" style={{textDecoration: 'none'}}>TicketWarrior</Link></li>
                <li><Link to="/login">Login</Link></li>
                <li><Link to="/register">Register</Link></li>
                <li><Link to="/OrderHistoryPage">Order History</Link></li>
            </ul>
            <div className="nav-bar-line"></div>
        </nav>
    )
}

export default Navbar;