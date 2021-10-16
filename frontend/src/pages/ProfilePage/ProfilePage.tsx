import React from 'react';
import {Header, Footer, Button} from "../../components";
import sampleImg from "../../assets/img/sample-preview-img.jpg"

import './ProfilePage.scss';
import ImgPreview from "../../components/ImgPreview";

const ProfilePage: React.FC = (props) => {
    return(
        <div className='wrapper-page'>
            <Header/>
            <section className='content'>
                <div className='container'>
                    <div className='profile'>
                        <div>
                            <div className='profile__img-container'>
                                <span className='icon-like-filled profile__like'/>
                                <ImgPreview imgPreviewSrc={sampleImg}/>
                            </div>
                            <Button className='profile__btn'>Message</Button>
                        </div>
                        <div className='profile__info'>
                            <div className='profile__name-container'>
                                <h2 className='profile__name'>Duc Truong</h2>
                                <span className='profile__icon-edit icon-edit'/>
                            </div>
                            <div className='profile__description'>
                                Description lorem ipsum lorem ipsum lorem ipsum. Description lorem ipsum lorem ipsum lorem ipsum
                            </div>
                        </div>
                    </div>
                    <div className='img-container'>
                        <ImgPreview imgPreviewSrc={sampleImg}/>
                        <ImgPreview imgPreviewSrc={sampleImg}/>
                        <ImgPreview imgPreviewSrc={sampleImg}/>
                        <ImgPreview imgPreviewSrc={sampleImg}/>
                        <ImgPreview imgPreviewSrc={sampleImg}/>
                        <ImgPreview imgPreviewSrc={sampleImg}/>
                        <ImgPreview imgPreviewSrc={sampleImg}/>
                        <ImgPreview imgPreviewSrc={sampleImg}/>
                    </div>
                </div>
            </section>
            <Footer/>
        </div>
    );
}

export default ProfilePage;
