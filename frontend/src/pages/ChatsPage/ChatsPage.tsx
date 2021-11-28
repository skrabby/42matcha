import React, {KeyboardEventHandler, useEffect, useRef, useState} from 'react';
import {Header, Footer, Button} from "../../components";
import {RouteComponentProps, withRouter} from "react-router";

import * as api from './api';
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

type IChats = {
    [chatId: string]: {
        chatId: string;
        message: {
            messageId: string,
            text: string,
            authorId: string,
            authorName: string,
            createdAt: string
        }
    }
};

const ChatsPage = (props: ChatPageProps) => {
    const {profileId} = props.match.params;
    const [chats, setChats] = useState<IChats>();
    const [connection, setConnection] = useState<WebSocket>();
    const [wsConnected, setWsConnected] = useState<boolean>(false);

    useEffect(() => {
        api.getUserChats()
            .then((chatIds: any) => {
                const connection = new WebSocket(`ws://127.0.0.1:1337?profileId=${profileId}&chats=${JSON.stringify(chatIds)}`);
                connection.onopen = () => {
                    setWsConnected(true);
                }

                connection.onmessage = (message) => {
                    let json;
                    let updChats: any = chats;

                    try {
                        json = JSON.parse(message.data.toString());
                    } catch (e) {
                        console.log('This doesn\'t look like a valid JSON: ', message.data);
                        return;
                    }

                    if (!updChats) {
                        updChats = {};
                    }

                    console.log(json);
                    // filling all chats
                    if (Array.isArray(json)) {
                        json.forEach((chat) => {
                            updChats[chat.chatId] = chat;
                        })
                    } else {
                        // updating chat if someone sent a message
                        updChats[json.chatId] = json;
                    }
                    setChats(updChats);
                }

                setConnection(connection);
            });
    },[])

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
        connection && connection.close();
    }

    return (
        <div className='wrapper-page'>
            <Header/>
            <section className='content'>
                <div className='chats'>
                    {!chats && (
                        <div style={{marginTop: '10px', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                            <div className="lds-heart"><div/></div>
                        </div>
                    )}
                    {chats && Object.keys(chats).length === 0 && (
                        <div className="chats__no-chats">You don't have any chats yet.</div>
                    )}
                    {chats && Object.entries(chats).map(([key, chat]) => (
                        <div key={key} className="chats__chat" onClick={() => onChatClick(chat.chatId)}>
                            <div className="chat-container">
                                <div className='chats__container-sender'>
                                    <div
                                        className={`chats__author ${chat.message.authorId !== profileId ? 'chats__author_other' : ''}`}>
                                        {chat.message.authorName}
                                    </div>
                                    <div className="chats__time">{formatDate(new Date(chat.message.createdAt))}</div>
                                </div>
                                <div className="chats__text">{formatText(chat.message.text)}</div>
                            </div>
                        </div>
                    ))}
                </div>
            </section>
        </div>
    );
}

export default withRouter(ChatsPage);
