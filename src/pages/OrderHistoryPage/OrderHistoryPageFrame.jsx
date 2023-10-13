import React from "react";
import OrderHistoryPageTable from "./OrderHistoryPageTable";
import OrderHistoryPageStartText from "./OrderHistoryPageStartText";
import { Link } from 'react-router-dom'
import NavBarLoggedIn from "../NavBar/NavBarLoggedIn";

function OrderHistoryPageFrame() {
    return (
        <>
            <NavBarLoggedIn/>
            <OrderHistoryPageStartText/>
            <OrderHistoryPageTable/>
        </>
    )
}

export default OrderHistoryPageFrame;