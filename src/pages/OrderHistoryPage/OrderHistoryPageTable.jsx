import React from "react";

import './tableStyles.css'

function OrderHistoryPageTable() {
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