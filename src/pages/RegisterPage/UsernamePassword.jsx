import React from "react";
import { Link } from 'react-router-dom'
import './styles.css'

function UsernamePassword() {
    return (
        <>
            <div className="description">
                <h1>Create an account now!</h1>
                <h3>Already have an account? <Link to="/login">Login here!</Link></h3>
            </div>

            <form>
                <div className="login-details">
                    <label for="inputUsername" class="form-label">Username:</label>
                    <input placeholder="Enter Username" type="text" class="login-input" id="inputUsername" value="" />
                    <label for="inputPassword" class="form-label">Password:</label> 
                    <input placeholder="Enter Password" type="text" class="login-input" id="inputPassword" value=""/>

                    <div className="login-button">
                        <button type="submit">Register</button>
                    </div>
                </div>
            </form>
        </>
        

    )
}

export default UsernamePassword