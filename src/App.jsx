import React from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from "./pages/HomePage/Home"
import Login from "./pages/LoginPage/Login"
import Register from "./pages/RegisterPage/Register"

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" exact element={<Home />} />
                <Route path="/login" exact element={<Login />} />
                <Route path="/register" exact element={<Register />} />
            </Routes>
        </Router>
    ) 
}

export default App;
