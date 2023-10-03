import React from "react";

import './tableStyles.css'

import axios, { formToJSON } from 'axios';

function OrderHistoryPageTable() {

    const username = localStorage.getItem('username');

    getUserID = (username) => {
        try {
             
             
            
        } catch {
            alert("Issue with getting UserID from using username");
        }
    }

    getOrdersFromDB = (username) => {
        try {
          const accessToken = localStorage.getItem('accessToken');
          const headers = {
            'Authorization': `Bearer ${accessToken}`
          };
          axios.get("http://localhost:8080/api/v1/orderHistory/users/{userId}/orders", {
            row: seat.row,
            coloumn: seat.num,
            type: "standard",
            availability: false,
            username: username,
            movieName: movieName
          }, {
            headers: headers,
            validateStatus: function (status) {
    
              return true; // Resolve only if the status code is less than 500
            }
          }).then(console.log);
        } catch {
          alert("how did you get here? please report steps done to team");
        }
    }



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