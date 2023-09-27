import React from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from "./pages/Home/Home"
import Login from "./pages/LoginPage/Login"
import Register from "./pages/RegisterPage/Register"
import SeatPage from "./pages/SeatingPage/SeatPage"
import OrderHistoryPageFrame from "./pages/OrderHistoryPage/OrderHistoryPageFrame";
import TimingPage from "./pages/TimingPage/TimingPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" exact element={<Home />} />
                <Route path="/login" exact element={<Login />} />
                <Route path="/register" exact element={<Register />} />
                <Route path="/seating" exact element={<SeatPage />} />
                <Route path="/OrderHistoryPage" exact element={<OrderHistoryPageFrame/>} />
                <Route path="/Timing" exact element={<TimingPage/>}/>
            </Routes>
        </Router>
    ) 
}

export default App;
