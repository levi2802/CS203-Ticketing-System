import React from "react";
import UsernamePassword from "./UsernamePassword"
import './styles.css';
import logo from './images/logo.png';
import {Link} from "react-router-dom";

function Register() {

    return (
        <div className="register">
            <div className="background">
                <Link to="/"><img
                    className="logo"
                    src={logo}
                    alt="logo"
                /></Link>

                <div className="gradient"></div>
            </div>

            <div className="register-body">
                <UsernamePassword/>
            </div>
        </div>
    )
}

export default Register