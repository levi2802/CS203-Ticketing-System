import React from "react";
import { Link } from 'react-router-dom'
import './styles.css'

function OrderHistoryPageStartText() {
    return (
        <>
            <div className="description">
                

            <h1>Order History Overview</h1>
            <h3>Can't find your previous bookings? Please <Link to="/login">sign in</Link> to view them.</h3>
            </div>
        </>
    )
}

export default OrderHistoryPageStartText