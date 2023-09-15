import React from "react";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { Carousel } from 'react-responsive-carousel';
import { Link } from 'react-router-dom';
import sample1 from "./images/image1.png";
import sample2 from "./images/image2.png";
import sample3 from "./images/image3.png";
import "./styles.css";


function MyCarousel() {
    return (
        <Carousel className="carousel" autoPlay={true} showArrows={false} showindicators={true} showStatus={false} showThumbs={false}>
             <div>
                <img src={sample1} alt="" style={{ height: "500px", width: "1050px" }} />
            </div>
            <div>
                <img src={sample2} alt="" style={{ height: "500px", width: "1050px" }} />
            </div>
            <div>
                <img src={sample3} alt="" style={{ height: "500px", width: "1050px" }} />
            </div>
        </Carousel>
    );
}

export default MyCarousel;
