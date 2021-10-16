import React from 'react';
import img1 from '../../assets/img/slider-img-1.png';

import './ImgSlider.scss';
import {Button} from "../Button";

const ImgSlider: React.FC = (props) => {
    return (
        <div className='slider'>
            <div>
                <p className='slider__subtitle'>GET YOUR</p>
                <h1 className='slider__title'>UNFORGETTABLE EMOTIONS</h1>
                <p className='slider__subtitle'>WHAT ARE YOU WAITING FOR?</p>
                <Button className='slider__btn'>JOIN NOW</Button>
            </div>
        </div>
    );
}

export default ImgSlider;