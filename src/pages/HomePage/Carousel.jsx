import React from "react";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { Carousel } from 'react-responsive-carousel';

function MyCarousel() {
    return (
        <Carousel>
            <div>
                <img src="./images/image1.png" alt="" />
                <p className="legend">Legend 1</p>
            </div>
            {/* <div>
                <img src="images/image2.png" alt="" />
                <p className="legend">Legend 2</p>
            </div>
            <div>
                <img src="images/image3.png" alt="" />
                <p className="legend">Legend 3</p>
            </div> */}
        </Carousel>
    );
}

export default MyCarousel;
