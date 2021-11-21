import React, {KeyboardEventHandler, useEffect, useRef, useState} from 'react';
import {Header, Footer, Button} from "../../components";
import {RouteComponentProps, withRouter} from "react-router";

import * as Router from '../../components/Router/constants';
import './ChatsPage.scss';

type ChatPageProps = RouteComponentProps & {
    match: {
        params: {
            profileId: string,
            chatId: string
        }
    }
}

const ChatsPage = (props: ChatPageProps) => {
    const {profileId} = props.match.params;

    const formatDate = (time: Date) => {
        const date = time.getDate() < 10 ? `0${time.getDate()}` : time.getDate();
        const month = time.getMonth() < 10 ? `0${time.getMonth()}` : time.getMonth();
        const hour = time.getHours() < 10 ? `0${time.getHours()}` : time.getHours();
        const minutes = time.getMinutes() < 10 ? `0${time.getMinutes()}` : time.getMinutes();

        return `${date}/${month}/${time.getFullYear()} ${hour}:${minutes}`;
    }

    const formatText = (text: string) => {
        return text.length > 64 ? text.substr(0, 64) + '...' : text;
    }

    const onChatClick = (chatId: string) => {
        props.history.push(Router.SET_PROFILE_CHAT(profileId, chatId));
    }

    return (
        <div className='wrapper-page'>
            <Header/>
            <section className='content'>
                <div className='chats'>
                    {[...Array(12)].map(() => (
                        <div className="chats__chat" onClick={() => onChatClick('chatId')}>
                            <div className="chat-container">
                                <div className='chats__container-sender'>
                                    <div className={`chats__author`}>{'Duc'}</div>
                                    <div className="chats__time">{formatDate(new Date())}</div>
                                </div>
                                <div className="chats__text">{formatText('asfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasfasf')}</div>
                            </div>
                        </div>
                    ))}
                </div>
            </section>
        </div>
    );
}

export default withRouter(ChatsPage);
