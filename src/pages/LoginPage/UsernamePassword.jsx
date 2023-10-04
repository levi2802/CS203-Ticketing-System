import React, { useState } from "react";
import { Link, useNavigate } from 'react-router-dom';
import './styles.css';
import axios from 'axios';

function UsernamePassword() {

    const API_URL = "52.221.230.42:8080/api/auth/";

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
        axios.post('52.221.230.42:8080/api/auth/authenticate', formData).then(response => {
            console.log(response.data);
            if (response.data.isSuccessful) {
                localStorage.setItem('accessToken', response.data.token);
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