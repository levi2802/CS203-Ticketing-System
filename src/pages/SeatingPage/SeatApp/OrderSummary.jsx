import React from 'react';
import Timer from './Timer';
import Button from '@mui/material/Button';
import './SeatApp.css';


function OrderSummary({ chosenSeats, rowName, chosenRow, handleCancel, handleConfirm, showSummary }) {
  return (
    <div className='orderSummary' style={{ display: showSummary ? 'block' : 'none' }}>
      <Timer />
      <pre>
        Qty: {chosenSeats.length}
      </pre>
      <pre>
        Seats: {chosenSeats.map((seatNum, index) => {
          const rowLabel = rowName[chosenRow[index] - 1];
          return `${rowLabel}${seatNum}`;
        }).join(", ")}
      </pre>
      <pre>
        Total cost: ${chosenSeats.length * 8}
      </pre>
      <Button variant="contained" onClick={handleCancel} style={{ background: 'grey' }}>Cancel</Button>
      <Button variant="contained" onClick={handleConfirm} style={{ background: 'grey' }}>Confirm</Button>
    </div>
  );
}

export default OrderSummary;
