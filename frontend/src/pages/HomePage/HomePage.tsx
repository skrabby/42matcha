import React from "react";
import * as actions from '../../store/actions'
import Header from '../../components/Header';
import Footer from '../../components/Footer';
import ImgSlider from "../../components/ImgSlider";
import ImgPreview from "../../components/ImgPreview";
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
}

class HomePage extends React.Component<HomePageProps, HomePageState> {
    render() {
        return (
            <div className='wrapper-primary'>
                <Header/>
                <div className='main'>
                    <section className='slider__main'>
                        <ImgSlider/>
                    </section>
                    <section className='samples'>
                        <div className='samples__outer-container'>
                            <h1 className='samples__title'>Popular profiles</h1>
                            <div className='samples__inner-container'>
                                <ImgPreview artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <ImgPreview artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <ImgPreview artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <ImgPreview artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <ImgPreview artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <ImgPreview artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <ImgPreview artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                                <ImgPreview artist='OneRepublic' name='Secrets' imgPreviewSrc={sampleImg} src='https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3'/>
                            </div>
                            <Button className='samples__btn'>VIEW ALL</Button>
                        </div>
                    </section>
                    <section className='samples'>
                        <div className='samples__outer-container'>
                            <h1 className='samples__title'>New profiles</h1>
                            <div className='samples__inner-container'>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
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