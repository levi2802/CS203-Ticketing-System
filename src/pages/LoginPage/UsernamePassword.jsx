import React, { useState } from "react";
import { Link, useNavigate } from 'react-router-dom';
import './styles.css';
import axios from 'axios';

function UsernamePassword() {
    //switch them based on which database you are testing on
    const API_URL = "http://localhost:8080";
    // const API_URL = "http://api.wicket.shop:8080";

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
        axios.post(API_URL + '/api/v1/auth/authentication', formData).then(response => {
            console.log(response.data);
            if (response.data.isSuccessful) {
                localStorage.setItem('accessToken', response.data.token);
                localStorage.setItem('email', response.data.email);
                localStorage.setItem('username', formData.username);
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
        <div className='signUp'>

            <form onSubmit={handleSubmit}>
                <h1>Sign In</h1>
                <input placeholder="Enter Username" type="text" className="login-input" name="username" value={formData.username} onChange={handleChange} />
                <input placeholder="Enter Password" type="password" className="login-input" name="password" value={formData.password} onChange={handleChange}/>
                <button type="submit">Sign In</button>

                <h4>
                    <span className='signUp-gray'>New to Wicked? </span>
                    <span ><Link className='signUp-link' to="/register">Sign Up Now.</Link></span>
                </h4>
            </form>
        </div>


    )
}

export default UsernamePassword