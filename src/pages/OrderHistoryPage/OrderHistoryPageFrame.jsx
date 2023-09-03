import React from "react";
import Navbar from "../HomePage/Navbar";
import OrderHistoryPageTable from "./OrderHistoryPageTable";
import OrderHistoryPageStartText from "./OrderHistoryPageStartText";
import { Link } from 'react-router-dom'

function OrderHistoryPageFrame() {
    return (
        <>
            <Navbar/>
            <OrderHistoryPageStartText/>
            <OrderHistoryPageTable/>
        </>
    )
}

export default OrderHistoryPageFrame;