import React from "react";
import { useState } from "react";

import './tableStyles.css'

import axios, { formToJSON } from 'axios';

import { json } from "react-router-dom";
import { responsiveFontSizes } from "@mui/material";

function OrderHistoryPageTable() {

    const username = localStorage.getItem('username');

    const [userID, setUserID] = useState(0);

    const [userPurchases, setUserPurchases] = useState(0);

    // let testArray = ["movieId", "List", "timestamp"];
    // console.log(testArray.toString());

    // setUserID("userID not successfully changed");

    // getUserIDByUsername(username);
    // userID = this.getUserIDByUsername(username);

    // console.log("userID = " + userID);

    getPurchasesByUsername(username);

    if (userPurchases) {
        console.log(userPurchases);
    }

    async function getUserIDByUsername(username) {
        try {

            // Ensure User is logged in before being able to get UserID
            const accessToken = localStorage.getItem('accessToken');
            const headers = {
                'Authorization': `Bearer ${accessToken}`
            };

            // Attempt to get User by Username
            let response = await axios.get("http://localhost:8080/api/v1/users/getUserByUsername/" + username, {
                        headers: headers,
                        validateStatus: function (status) {
                            return true; // Resolve only if the status code is less than 500
                        }
                    }
                );

            setUserID(response.data.id);
            console.log("Successfully set userID to " + response.data.id);
            
        } catch(exception) {
            // alert("Issue with getting UserID from using username");
            console.log(exception.name);
            console.log(exception.message);
        }
    }

    async function getPurchasesByUsername(username) {
        try {

            // Ensure User is logged in before being able to get Purchases
            const accessToken = localStorage.getItem('accessToken');
            const headers = {
                'Authorization': `Bearer ${accessToken}`
            };

            const purchaseArray = [];

            // Attempt to get Purchases by UserID
            let response = await axios.get("http://localhost:8080/api/v1/purchases/" + username, {
                        headers: headers,
                        validateStatus: function (status) {
                            // console.log("status validated");
                            return true; // Resolve only if the status code is less than 500
                        }
                    }
                );
            response.data.forEach(purchase => purchaseArray.push([purchase.timestamp, purchase.movieId, purchase.seatIDs]));

            console.log(response);

            setUserPurchases(purchaseArray);
            
        } catch(exception) {
            console.log(exception.name);
            console.log(exception.message);
        }
    }

    // getOrdersFromDB = (username) => {
    //     try {
    //       const accessToken = localStorage.getItem('accessToken');
    //       const headers = {
    //         'Authorization': `Bearer ${accessToken}`
    //       };
    //       axios.get("http://localhost:8080/api/v1/orderHistory/users/{userId}/orders", {
    //         row: seat.row,
    //         coloumn: seat.num,
    //         type: "standard",
    //         availability: false,
    //         username: username,
    //         movieName: movieName
    //       }, {
    //         headers: headers,
    //         validateStatus: function (status) {
    
    //           return true; // Resolve only if the status code is less than 500
    //         }
    //       }).then(console.log);
    //     } catch {
    //       alert("how did you get here? please report steps done to team");
    //     }
    // }



    return (
        <>
            <table>
                <thead>
                    <th>Purchase Date and Time</th>
                    <th>Movie Title</th>
                    <th>Seats Chosen</th>
                    <th>Total Price</th>
                </thead>
                <tr>
                    <td>Placeholder DateTime</td>
                    <td>Placeholder Title</td>
                    <td>Placeholder Seats</td>
                    <td>Placeholder Price</td>
                </tr>
                <tr>
                    <td>Placeholder DateTime 2</td>
                    <td>Placeholder Title 2</td>
                    <td>Placeholder Seats 2</td>
                    <td>Placeholder Price 2</td>
                </tr>
                <tr>
                    <td>Placeholder DateTime 3</td>
                    <td>Placeholder Title 3</td>
                    <td>Placeholder Seats 3</td>
                    <td>Placeholder Price 3</td>
                </tr>
                <tr>
                    <td>Placeholder DateTime 4</td>
                    <td>Placeholder Title 4</td>
                    <td>Placeholder Seats 4</td>
                    <td>Placeholder Price 4</td>
                </tr>
            </table>
        </>
    )
}

export default OrderHistoryPageTable