import React from "react";
import { Link } from 'react-router-dom'
import './styles.css'

function UsernamePassword() {
    return (
        <>
            <div className="description">
                <h1>Enter your login details</h1>
                <h3>Don't have an account? <Link to="/register">Sign up now!</Link></h3>
            </div>

            <form>
                <div className="login-details">
                    <label for="inputUsername" class="form-label">Username:</label>
                    <input placeholder="Enter Username" type="text" class="login-input" id="inputUsername" value="" />
                    <label for="inputPassword" class="form-label">Password:</label> 
                    <input placeholder="Enter Password" type="text" class="login-input" id="inputPassword" value=""/>

                    <div className="login-button">
                        <button type="submit" class="btn btn-primary mt-4 mb-4 mx-auto d-block">Login</button>
                    </div>
                </div>
            </form>
        </>
        

    )
}

export default UsernamePassword