import React, { useState } from "react";
import { Link, useNavigate } from 'react-router-dom';
import './styles.css';
import axios from 'axios';

function UsernamePassword() {
    //switch them based on which database you are testing on
    const API_URL = "http://localhost:8080";
    // const API_URL = "http://api.wicket.shop:8080";

    const initialFormData = {
        email: '',
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

    const [setErrors] = useState({});

    const handleSubmit = event => {
        event.preventDefault();
        axios.post(API_URL + "/api/v1/auth/registration", formData).then(response => {
            console.log(response.data);
            if (response.data.isSuccessful) {
                localStorage.setItem('accessToken', response.data.token);
                localStorage.setItem('email', response.data.email);
                localStorage.setItem('username', formData.username);
                setData(initialFormData);
                alert("Registration successful!");
                navigate('/');
            } else {
                alert("Registration failed. Please enter a different username/email.");
                setData(initialFormData);
            }

        }).catch(error => {
            if (error.response && error.response.data) {
                setErrors(error.response.data);
            }
            alert("Please ensure you enter a proper email. Password must be at least 8 characters long and contain at least one letter and one number");
            console.log('Error: ', error);
        })

    };

    //const [message, setMessage] = useState(''); // State variable to store the message

    return (
        <div className='signUp'>

            <form onSubmit={handleSubmit}>
                <h1> Register </h1>
                <input placeholder="Enter Email" type="text" className="login-input" name="email" value={formData.email} onChange={handleChange} />
                <input placeholder="Enter Username" type="text" className="login-input" name="username" value={formData.username} onChange={handleChange} />
                <input placeholder="Enter Password" type="password" className="login-input" name="password" value={formData.password} onChange={handleChange}/>
                <button type="submit">Register</button>

                <h4>
                    <span className='signUp-gray'>Already got an account? </span>
                    <span><Link className="signUp-link" to="/login">Sign In Now.</Link></span>
                </h4>
            </form>
        </div>
    )
}

export default UsernamePassword