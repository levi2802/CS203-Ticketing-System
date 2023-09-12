import React, { useState, useEffect } from "react";
import { Link, useNavigate } from 'react-router-dom';
import './styles.css';
import axios from 'axios';

function UsernamePassword() {

    const API_URL = "http://localhost:8080/api/auth/";

    const initialFormData = {
        username: '',
        password: ''
    };

    const navigate = useNavigate();

    const [formData, setData] = useState(initialFormData);

    const handleChange = event => {
        const { name, value } = event.target;
        setData(prevState => ({
            ...prevState,
            [name] : value
        }));
    };

    const handleSubmit = event => {
        event.preventDefault();
        axios.post(API_URL + "register", formData).then(response => {
            console.log(response.data);
            if (response.data.isSuccessful) {
                localStorage.setItem('accessToken', response.data.token);
                setData(initialFormData);
                alert("Registration successful!");
                navigate('/');
            } else {
                alert("Registration failed. Please enter a different username.");
                setData(initialFormData);
            }

        }).catch(error => {
            console.log('Error: ', error);
        })
        
    };

    const [message, setMessage] = useState(''); // State variable to store the message

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