import React from 'react';

import './TariffCard.scss';
import {Button} from "../Button";

interface TariffCardProps {
    title: string;
    period: 'month' | 'year' | 'once'
    price: number;
    bodyText: string;
    className?: string;
    headerMsg?: string;
    isActive?: boolean;
    isAnimated?: boolean;
}

const TariffCard: React.FC<TariffCardProps> = (props) => {
    return (
        <div className={`tariff-card ${props.className ?? ''} \
        ${props.isActive ? 'tariff-card__lg' : ''} \
        ${props.isAnimated? 'tariff-card__lg_animated' : ''}`}>
            <div className='tariff-card__top'>
                {props.headerMsg &&
                    <div className='msg'>{props.headerMsg}</div>
                }
                <div className='container'>
                    <h1 className='title'>{props.title}</h1>
                    <div className='text-container'>
                        <div className='currency'>$</div>
                        <div className='price'>{props.price}</div>
                        <div className='period'>{`/${props.period}`}</div>
                    </div>
                </div>
            </div>
            <div className='tariff-card__bottom'>
                <p className='text'>{props.bodyText}</p>
                <Button className='tariff-btn'>GET STARTED</Button>
            </div>
        </div>
    );
}

export default TariffCard;