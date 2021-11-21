import React, {KeyboardEventHandler, useEffect, useRef, useState} from 'react';
import {Header, Footer, Button} from "../../components";
import {RouteComponentProps, withRouter} from "react-router";

import './ChatPage.scss';
import * as Router from "../../components/Router/constants";

type ChatPageProps = RouteComponentProps & {
    match: {
        params: {
            profileId: string,
            chatId: string
        }
    }
}

const ChatPage = (props: ChatPageProps) => {
    const {profileId, chatId} = props.match.params;
    const [chatContent, setChatContent] = useState<JSX.Element[]>([]);
    const [showInput, setShowInput] = useState<boolean>(true);
    const [connection, setConnection] = useState<WebSocket>();
    const [wsConnected, setWsConnected] = useState<boolean>(false);
    const chatContentRef = useRef<HTMLDivElement | null>(null);
    const input = useRef<HTMLInputElement | null>(null);

    const formatDate = (time: Date) => {
        const date = time.getDate() < 10 ? `0${time.getDate()}` : time.getDate();
        const month = time.getMonth() < 10 ? `0${time.getMonth()}` : time.getMonth();
        const hour = time.getHours() < 10 ? `0${time.getHours()}` : time.getHours();
        const minutes = time.getMinutes() < 10 ? `0${time.getMinutes()}` : time.getMinutes();

        return `${date}/${month}/${time.getFullYear()} ${hour}:${minutes}`;
    }

    const addMessage = (author: string, authorId: string, text: string, time: Date, ) => {
        const messageEl = (
            <div key={chatContent.length} className="chat__message">
                <div className='chat__container-sender'>
                    <div className={`chat__author ${profileId === authorId ? '' : 'chat__author_other'}`}>{author}</div>
                    <div className="chat__time">{formatDate(time)}</div>
                </div>
                <div className="chat__text">{text}</div>
            </div>
        );
        chatContent.push(messageEl)
        setChatContent([...chatContent]);
    }

    const onInputKeyPress = (e: any) => {
        if (!e.target.value) return;
        if (e.key === 'Enter') {
            connection && connection.send(JSON.stringify({message: e.target.value, chatId, authorId: profileId, authorName: 'Duc'}));
            e.target.value = '';
        }
    }

    const onBackBtnClick = () => {
        props.history.push(Router.SET_CHATS(profileId));
        connection && connection.close();
    }

    useEffect(() => {
        // if browser doesn't support WebSocket, just show some notification and exit
        if (!window.WebSocket) {
            setChatContent([<p className="chat__hint">Sorry, but your browser doesn\'t support WebSockets</p>]);
            setShowInput(false);
            return;
        }

        const connection = new WebSocket(`ws://127.0.0.1:1337?profileId=${profileId}&chatId=${chatId}`);

        connection.onopen = () => {
            setWsConnected(true);
        }

        connection.onerror = () => {
            setChatContent([<p className="chat__hint">Sorry, but there's some problem with your connection or the server
                is down.</p>]);
            setShowInput(false);
        }

        connection.onmessage = (message) => {
            let json;
            try {
                json = JSON.parse(message.data.toString());
            } catch (e) {
                console.log('This doesn\'t look like a valid JSON: ', message.data);
                return;
            }
            if (Array.isArray(json)) {
                if (json.length > 0) {
                    json.forEach(msg => {
                        addMessage(msg.message.authorName, msg.message.authorId, msg.message.text, new Date(msg.message.createdAt));
                    })
                } else {
                    setChatContent([<p className="chat__hint">Don't keep your partner waiting. Hurry up and send a
                        message first!</p>]);
                }
            } else {
                addMessage(json.message.authorName, json.message.authorId,json.message.text, new Date(json.message.createdAt));
                input.current?.focus();
            }
            if (chatContentRef.current) {
                chatContentRef.current.scrollTop = chatContentRef.current.scrollHeight;
            }
        }

        setConnection(connection);
    }, [])

    return (
        <div className='wrapper-page'>
            <Header/>
            <section className='content'>
                <div className='container'>
                    <div className='chat'>
                        <div key={chatContent.length} className='chat__wrapper'>
                            <div className='chat__banner'>
                                <span onClick={onBackBtnClick} className='chat__back-btn'>{'Back'}</span>
                                <span onClick={() => console.log('V')} className='chat__receiver'>{'Vickie'}</span>
                            </div>
                            <div ref={chatContentRef} className='chat__content'>
                                {!wsConnected && (
                                    <div style={{marginTop: '10px', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                        <div className="lds-heart"><div/></div>
                                    </div>
                                )}
                                {wsConnected && chatContent}
                            </div>
                            {showInput && (
                                <div className="chat__input-container">
                                    <input ref={input} onKeyPress={onInputKeyPress}
                                           autoComplete="off"
                                           disabled={!wsConnected}
                                           placeholder='Type your message here...' className='chat__input' type="text"
                                           id="input"/>
                                </div>
                            )}
                        </div>
                    </div>
                    <div>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default withRouter(ChatPage);
