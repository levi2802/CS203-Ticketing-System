import React, { useRef } from 'react';
import Timer from '../Timer';
import Button from '@mui/material/Button';
import '../SeatApp.css';
import ReCAPTCHA from "react-google-recaptcha";
import axios from 'axios';


function OrderSummary({ chosenSeats, rowName, chosenRow, handleCancel, handleConfirm, showSummary }) {
  const captchaRef = useRef(null)

  // Getting the response token on submit of form
  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = captchaRef.current.getValue();
    //console.log(token);
    captchaRef.current.reset();

    await axios.post("http://localhost:8080/api/v1/captcha/verification", { token })
      .then(res => {
        console.log(res);
        handleConfirm();
      })
      .catch(error => {
        console.log(error);
        alert("Please do the captcha verification!");
      });
  }

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

      <form className='form' onSubmit={handleSubmit}>
        <ReCAPTCHA className="captcha" sitekey={process.env.REACT_APP_SITE_KEY} ref={captchaRef} />
        <div className='Buttons'>
          <Button variant="contained" onClick={handleCancel} style={{ background: 'grey' }}>Cancel</Button>
          <Button type="submit" variant="contained" style={{ background: 'grey' }}>Confirm</Button>
        </div>
        
      </form>
    </div>
  );
}

export default OrderSummary;
