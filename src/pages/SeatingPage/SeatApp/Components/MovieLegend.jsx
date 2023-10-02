import React from 'react';
import '../SeatApp.css';

function MovieLegend() {
    return(
        <div>
            <h3>Legend</h3>
            <pre>
              <div id='Unavailable' style={{ display: 'inline-block' }}></div>Unavailable
              <div id='AvailableLegend' style={{ display: 'inline-block', marginLeft: '10px' }}></div>Available
              <div id='selected' style={{ display: 'inline-block', marginLeft: '10px' }}></div>Selected
            </pre>
        </div>
    )
}

export default MovieLegend;