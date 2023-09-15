import React from "react";
import { Link } from 'react-router-dom'
import './styles.css'

function OrderHistoryPageStartText() {
    return (
        <>
            <div className="description">
                

            <h1>Your Order History:</h1>
            <h3>Don't see your order history? <Link to="/login">Log into your account first!</Link></h3>
            </div>
        </>
    )
}

export default OrderHistoryPageStartText