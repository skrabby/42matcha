import React from "react";
import * as actions from '../../store/actions'
import Header from '../../components/Header';
import Footer from '../../components/Footer';
import ImgSlider from "../../components/ImgSlider";
import Sample from "../../components/Sample";
import TariffCard from "../../components/TariffCard";
import TariffCardsSlider from "../../components/TariffCardsSlider";
import sampleImg from "../../assets/img/sample-preview-img.jpg"
import followUsImg from "../../assets/img/follow.png";

import './HomePage.scss';
import {Button} from "../../components";
import LoginPage from "../LoginPage";

interface HomePageProps {
}

interface HomePageState {
    isLoginModalActive: boolean;
}

class HomePage extends React.Component<HomePageProps, HomePageState> {
    constructor(props: HomePageProps) {
        super(props);

        this.state = {
            isLoginModalActive: false
        }
    }

    onLoginModalClick = (isActive: boolean) => {
        this.setState({isLoginModalActive: isActive});
    }

    render() {
        return (
            <div className='wrapper-primary'>
                <div className={`modal-login${this.state.isLoginModalActive ? '' : ' modal-login_disabled'}`}>
                    <div className='modal-login__mask'
                         onClick={() => this.onLoginModalClick(false)}>
                        <span className='modal-login__close-btn'/>
                    </div>
                    <LoginPage/>
                </div>
                <Header onProfileClick={() => this.onLoginModalClick(true)}/>
                <div className='main'>
                    <section className='slider__main'>
                        <ImgSlider/>
                    </section>
                    <section className='samples'>
                        <div className='samples__outer-container'>
                            <h1 className='samples__title'>Popular Sheets</h1>
                            <div className='samples__inner-container'>
                                <Sample artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <Sample artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <Sample artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <Sample artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <Sample artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <Sample artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <Sample artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <Sample artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                            </div>
                            <Button className='samples__btn'>VIEW ALL</Button>
                        </div>
                    </section>
                    <section className='tariffs'>
                        <TariffCardsSlider
                            tariffCards={[
                                <TariffCard
                                    key={1}
                                    period={'month'}
                                    price={12}
                                    title={'Monthly'}
                                    bodyText={'Get access to all your favourite songs for a full month!'}
                                />,
                                <TariffCard
                                    key={2}
                                    period={'year'}
                                    price={97}
                                    title={'Annual'}
                                    bodyText={'Only 8.08$ per month. Save up more than 32%!'}
                                    headerMsg={'Most Popular'}
                                />,
                                <TariffCard
                                    key={3}
                                    period={'once'}
                                    price={297}
                                    title={'Lifetime'}
                                    bodyText={'Pay only once and get your PERMANENT access to the whole library!'}
                                />
                            ]}
                        />
                    </section>
                    <section className='samples'>
                        <div className='samples__outer-container'>
                            <h1 className='samples__title'>Free Sheets</h1>
                            <div className='samples__inner-container'>
                                <Sample imgPreviewSrc={sampleImg}/>
                                <Sample imgPreviewSrc={sampleImg}/>
                                <Sample imgPreviewSrc={sampleImg}/>
                                <Sample imgPreviewSrc={sampleImg}/>
                                <Sample imgPreviewSrc={sampleImg}/>
                                <Sample imgPreviewSrc={sampleImg}/>
                                <Sample imgPreviewSrc={sampleImg}/>
                                <Sample imgPreviewSrc={sampleImg}/>
                            </div>
                            <Button className='samples__btn'>VIEW ALL</Button>
                        </div>
                    </section>
                </div>
                <Footer/>
            </div>
        )
    }
}

const mapStateToProps = (state: any) => ({
    isMainLoaderActive: state.loadersReducer.isMainLoaderActive
})

const mapDispatchToProps = (dispatch: any) => ({
    setMainLoaderState: (isActive: boolean = true) => (
        dispatch({
            type: "SET_MAIN_LOADER",
            payload: true
        })
    )
})

export default HomePage;