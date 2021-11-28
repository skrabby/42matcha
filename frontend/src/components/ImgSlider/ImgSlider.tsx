import React, {useState} from 'react';

import './ImgSlider.scss';
import {Button} from "../Button";
import LoginPage from "../../pages/LoginPage/LoginPage";
import * as Routes from "../Router/constants";
import {RouteComponentProps, withRouter} from "react-router";
import { Card } from '../../pages/LoginPage/LoginPage';

const ImgSlider = (props: RouteComponentProps) => {
    const [isLoginModalActive, setIsLoginModalActive] = useState<boolean>(false);

    const onBtnClick = () => {
        if (!localStorage.getItem("token") || !localStorage.getItem("profileId")) {
            setIsLoginModalActive(true)
        } else {
            props.history.push(Routes.SET_PROFILE(localStorage.getItem("profileId")!))
        }
    }

    return (
        <div className='slider'>
            <div>
                <div className={`modal-login${isLoginModalActive ? '' : ' modal-login_disabled'}`}>
                    <div className='modal-login__mask'
                         onClick={() => setIsLoginModalActive(false)}>
                        <span className='modal-login__close-btn'/>
                    </div>
                    <LoginPage card={Card.REGISTER}/>
                </div>
                <p className='slider__subtitle'>GET YOUR</p>
                <h1 className='slider__title'>UNFORGETTABLE EMOTIONS</h1>
                <p className='slider__subtitle'>WHAT ARE YOU WAITING FOR?</p>
                <Button onClick={onBtnClick} className='slider__btn'>JOIN NOW</Button>
            </div>
        </div>
    );
}

export default withRouter(ImgSlider);
