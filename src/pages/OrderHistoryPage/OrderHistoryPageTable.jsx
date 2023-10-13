import React, { useState, useEffect } from "react";
import './tableStyles.css';
import axios from 'axios';

function OrderHistoryPageTable() {
    const username = localStorage.getItem('username');
    const [userPurchases, setUserPurchases] = useState([]);

    useEffect(() => {
        if (username) {
            getPurchasesByUsername(username);
        }
    }, [username]);

    async function getPurchasesByUsername(username) {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const headers = {
                'Authorization': `Bearer ${accessToken}`
            };

            let response = await axios.get(`http://localhost:8080/api/v1/purchase/users/${username}/purchases`, {
                headers: headers,
                validateStatus: status => status < 500
            });

            setUserPurchases(response.data);
        } catch (exception) {
            console.log(exception.name);
            console.log(exception.message);
        }
    }

    return (
        <>
            <table>
                <thead>
                    <th>Purchase Date and Time</th>
                    <th>Movie Title</th>
                    <th>Movie Location</th>
                    <th>Movie Timing</th>
                    <th>Seats Chosen</th>
                    <th>Total Price</th>
                </thead>
                {userPurchases.map(purchase => (
                    <tr key={purchase.id}>
                        <td>{purchase.timestamp}</td>
                        <td>{purchase.movieId}</td>
                        <td>{purchase.location}</td>
                        <td>{purchase.timing}</td>
                        <td>{purchase.seatIDs}</td>
                        <td>{"$" + purchase.price}</td>
                    </tr>
                ))}
            </table>
        </>
    )
}

export default OrderHistoryPageTable;
