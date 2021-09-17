import React, {useEffect, useState} from 'react';

import './TariffCardsSlider.scss';

enum Arrow {
    LEFT,
    RIGHT
}

interface TariffCardsSliderProps {
    tariffCards: JSX.Element[]
}

const TariffCardsSlider: React.FC<TariffCardsSliderProps> = (props) => {
    const [tariffCards, setTariffCards] = useState(props.tariffCards);
    const [tariffCardsLastKey, setTariffCardsLastKey] = useState(0)
    const [cardsToShow, setCardsToShow] = useState<number>(3);
    const [isMobile, setIsMobile] = useState<boolean>(false);

    useEffect(() => {
        const adjustCardsQuantity = () => {
            if (window.innerWidth < 1200) {
                window.innerWidth < 760 && setIsMobile(true);
                setCardsToShow(1);
            } else {
                setCardsToShow(3);
                setIsMobile(false);
            }
        }
        adjustCardsQuantity();

        window.addEventListener('resize', (e) => {
           adjustCardsQuantity();
        })
    },[cardsToShow])

    const onTariffCardSwitch = (direction: Arrow) => {
        let selectedCard: JSX.Element;

        // disabling active tariff card
        tariffCards[1] = React.cloneElement(tariffCards[1], { isActive: false, isAnimated: false })

        if (direction == Arrow.RIGHT) {
            selectedCard = tariffCards.pop() ?? <React.Fragment/>;
            tariffCards.unshift(selectedCard);
        } else {
            selectedCard = tariffCards.shift() ?? <React.Fragment/>;
            tariffCards.push(selectedCard);
        }

        // enabling active tariff card
        tariffCards[1] = React.cloneElement(tariffCards[1], { isActive: true, isAnimated: true})
        setTariffCards(tariffCards);
        setTariffCardsLastKey(tariffCardsLastKey + 1);
    }

    return (
        <div className='tariffs__container' key={tariffCardsLastKey}>
            {!isMobile && <span className='icon-arrow icon-arrow__left' onClick={() => onTariffCardSwitch(Arrow.LEFT)}/>}
            {props.tariffCards.map((tariffCard, idx) => {
                if (cardsToShow == 3 && idx == 1) {
                    tariffCard = React.cloneElement(tariffCard, { isActive: true, isAnimated: true});
                } else if (cardsToShow < 3) {
                    tariffCard = React.cloneElement(tariffCard, { isActive: true });
                }
                return idx < cardsToShow && tariffCard;
            })}
            {!isMobile && <span className='icon-arrow icon-arrow__right' onClick={() => onTariffCardSwitch(Arrow.RIGHT)}/>}
        </div>
    );
}

export default TariffCardsSlider;