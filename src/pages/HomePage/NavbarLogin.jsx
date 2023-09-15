import React from "react";
import { Link } from 'react-router-dom'
import "./styles.css"
import profile from "../../images/user_profile.png"

function NavbarLogin() {

    const handleLogout = () => {
        localStorage.removeItem('accessToken');
        window.location.reload();
    }

    return (
        <nav>
            <ul className="nav-bar">
                <li className="nav-bar-name"> <Link to="/">TicketWarrior</Link> </li>
                <li> <img className="profile" src={profile} alt="user-profile" /> </li>
                <li> <button onClick={handleLogout}>Logout</button></li>
            </ul>
            <div className="nav-bar-line"></div>
        </nav>
    )
}

export default NavbarLogin