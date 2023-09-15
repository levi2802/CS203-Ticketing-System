import React, { useState } from "react";
import { Link, useNavigate } from 'react-router-dom';
import './styles.css';
import axios from 'axios';

function UsernamePassword() {

    const API_URL = "http://localhost:8080/api/auth/";

    const initialFormData = {
        email: 'null',
        username: '',
        password: ''
    };

    const navigate = useNavigate();

    const [formData, setData] = useState (initialFormData);

    const handleChange = event => {
        const { name, value } = event.target;
        setData(prevState => ({
            ...prevState,
            [name] : value
        }));
    };

    const handleSubmit = event => {
        event.preventDefault();
        axios.post('http://localhost:8080/api/auth/authenticate', formData).then(response => {
            console.log(response.data);
            if (response.data.isSuccessful) {
                localStorage.setItem('accessToken', response.data.token);
                setData(initialFormData);
                alert("Login successful!");
                navigate('/');
            } else {
                alert("Login Failed. Please try again.");
                setData(initialFormData);
            }
        }).catch(error => {
            console.log('Error: ', error);
        })

    };

    return (
        <>
            <div className="description">
                <h1>Enter your login details</h1>
                <h3>Don't have an account? <Link to="/register">Sign up now!</Link></h3>
            </div>

            <form onSubmit={handleSubmit}>
                <div className="login-details">
                    <label htmlFor="inputUsername" className="form-label">Username:</label>
                    <input placeholder="Enter Username" type="text" className="login-input" name="username" value={formData.username} onChange={handleChange} />
                    <label htmlFor="inputPassword" className="form-label">Password:</label>
                    <input placeholder="Enter Password" type="password" className="login-input" name="password" value={formData.password} onChange={handleChange}/>

                    <div className="login-button">
                        <button type="submit" className="btn btn-primary mt-4 mb-4 mx-auto d-block">Login</button>
                    </div>
                </div>
            </form>
        </>
        

    )
}

export default UsernamePassword