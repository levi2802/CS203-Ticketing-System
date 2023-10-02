import React from 'react';
import Timer from '../Timer';
import '../SeatApp.css';

function stage(){
    return(
        <div id="stage-container">
            <div><span><Timer /></span></div>
            <svg width="500" height="100" >
            </svg>
            <h1 id="stage">Screen</h1>
        </div>
    );
}

export default stage;