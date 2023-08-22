import React, { useState } from "react";
import { Link } from 'react-router-dom'
import './styles.css'

function UsernamePassword() {

    const [formData, setData] = useState (
        {
            username : '',
            password : ''
        }
    );

    const handleChange = event => {
        const { name, value } = event.target;
        setData(prevState => ({
            ...prevState,
            [name] : value
        }));
    };

    const handleSubmit = event => {
        event.preventDefault();
        console.log(formData.username);
    };

    return (
        <>
            <div className="description">
                <h1>Create an account now!</h1>
                <h3>Already have an account? <Link to="/login">Login here!</Link></h3>
            </div>

            <form onSubmit={handleSubmit}>
                <div className="login-details">
                    <label htmlFor="inputUsername" className="form-label">Username:</label>
                    <input placeholder="Enter Username" type="text" className="login-input" name="username" value={formData.username} onChange={handleChange} />
                    <label htmlFor="inputPassword" className="form-label">Password:</label> 
                    <input placeholder="Enter Password" type="password" className="login-input" name="password" value={formData.password} onChange={handleChange}/>

                    <div className="login-button">
                        <button type="submit">Register</button>
                    </div>
                </div>
            </form>
        </>
        

    )
}

export default UsernamePassword