import React, {useEffect, useState} from 'react';
import {Header, Footer, Button} from "../../components";
import imgPlaceholder from "../../assets/img/image-placeholder.jpg";
import {RouteComponentProps, withRouter} from "react-router";

import * as api from './api';
import * as Routes from '../../components/Router/constants'
import './ProfilePage.scss';
import ImgPreview from "../../components/ImgPreview";
import avaMale from "../../assets/img/ava-male-placehold.png";
import avaFemale from "../../assets/img/ava-female-placehold.png";

type ProfilePageProps = RouteComponentProps & {
    match: {
        params: {
            profileId: string;
        }
    }
}

const ProfilePage = (props: ProfilePageProps) => {
    const [user, setUser] = useState<any>();
    const { profileId } = props.match.params;

    useEffect(() => {

        api.getUserInfo(profileId)
            .then((user) => {
                setUser(user);
            })
    }, [])
    console.log(user);

    const onLikeClick = () => {
        api.setLike(profileId)
            .then((rs) => {
                alert(rs);
            });
    }

    if (!user) return null;

    return(
        <div className='wrapper-page'>
            <Header/>
            <section className='content'>
                <div className='container'>
                    <div className='profile'>
                        <div>
                            <div className='profile__img-container'>
                                <span onClick={onLikeClick} className='icon-like-filled profile__like'/>
                                <ImgPreview
                                    imgPreviewSrc={user.avatarUrl || user.gender === "MALE" ? avaMale : avaFemale}
                                    onChange={(e) => console.log(e.target.files[0])}
                                />
                            </div>
                            {localStorage.getItem("id") === profileId && (
                                <Button onClick={() => props.history.push(Routes.SET_CHATS(profileId))} className='profile__btn'>
                                    Chats
                                </Button>)
                            }
                            {localStorage.getItem("id") !== profileId && (
                                <Button
                                    onClick={() => props.history.push(Routes.SET_(profileId))} className='profile__btn'
                                >
                                    Message
                                </Button>)
                            }
                        </div>
                        <div className='profile__info'>
                            <div className='profile__name-container'>
                                <h2 className='profile__name'>{user.name}</h2>
                                <span className='profile__icon-edit icon-edit'/>
                            </div>
                            <div className='profile__main-info'>{`${user.gender}, ${user.orientation}, ${user.birthday}`}</div>
                            <div className='profile__tags'>
                                {user.tags.map((tag: string, i: number) => (
                                    <div key={i} className='profile__tag'>{tag}</div>
                                ))}
                            </div>
                            <div className='profile__description'>
                                {user.description}
                            </div>
                        </div>
                    </div>
                    <div className='img-container'>
                        <ImgPreview imgPreviewSrc={imgPlaceholder}/>
                        <ImgPreview imgPreviewSrc={imgPlaceholder}/>
                        <ImgPreview imgPreviewSrc={imgPlaceholder}/>
                        <ImgPreview imgPreviewSrc={imgPlaceholder}/>
                        <ImgPreview imgPreviewSrc={imgPlaceholder}/>
                        <ImgPreview imgPreviewSrc={imgPlaceholder}/>
                        <ImgPreview imgPreviewSrc={imgPlaceholder}/>
                        <ImgPreview imgPreviewSrc={imgPlaceholder}/>
                    </div>
                </div>
            </section>
            <Footer/>
        </div>
    );
}

export default withRouter(ProfilePage);
