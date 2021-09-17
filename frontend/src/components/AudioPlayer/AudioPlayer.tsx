import React, {useCallback, useEffect, useRef, useState} from 'react';

import './AudioPlayer.scss';

interface AudioPlayerProps {
    artist?: string;
    name?: string;
    src: string;
}

const AudioPlayer: React.FC<AudioPlayerProps> = (props) => {
    const [currentTime, setCurrentTime] = useState<number>(0) // in seconds
    const [duration, setDuration] = useState<number>(0)
    const [isPaused, setIsPaused] = useState<boolean>(true);
    const [audio, setAudio] = useState<HTMLAudioElement>();
    const BAR_WIDTH = 270;
    const MAX_TITLE_SIZE = 30;
    const onAudioChange = useCallback(audio => {
        if (audio) {
            setAudio(audio);
        }
    }, [audio])

    useEffect(() => {
        audio?.addEventListener('loadedmetadata', () => {
            setDuration(Math.floor(audio?.duration));
        })
        audio?.addEventListener('timeupdate', () => {
            setCurrentTime(Math.floor(audio?.currentTime));
        })
    }, [audio, currentTime, duration]);

    const onPlayBtnClick = () => {
        AudioPlayerInstance.onPlayBtnClick(audio, () => { setIsPaused(true); });
        setIsPaused(!isPaused);
    }

    const onTrackBarClick = (e: any) => {
        let rect = e.target.getBoundingClientRect();
        const cordX = e.pageX - rect.left;
        const mult = cordX / BAR_WIDTH;
        const currentTime = Math.floor(duration * mult);
        audio!.currentTime = currentTime;
        setCurrentTime(currentTime);
    }

    const getFilledBarWidth = () => {
        return Math.floor((currentTime / duration) * BAR_WIDTH);
    }

    const getFormattedTime = (secondsTotal: number) => {
        let seconds = secondsTotal % 60;
        let minutes = Math.floor(secondsTotal / 60);

        return `${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds}`;
    }

    const getFormattedAudioTitle = (artist: string, name: string) => {
        let formattedStr = `${artist} - ${name}`;
        return formattedStr.length > MAX_TITLE_SIZE ? formattedStr.substr(0, MAX_TITLE_SIZE) + '...' : formattedStr;
    }

    return (
        <div className='audio-player'>
            <div className='audio-player__btn-container' onClick={onPlayBtnClick}>
                <span className={`audio-player__btn-container-icon audio-player__btn-container-icon-${isPaused ? 'play' : 'pause'}`}/>
            </div>
            <div className='audio-player__track-container'>
                <div className='audio-player__track-container-top'>
                    <span className='audio-player__title'>{getFormattedAudioTitle(props.artist ?? 'Unknown', props.name ?? 'Unknown')}</span>
                    <span className='audio-player__timer'>
                        {getFormattedTime(currentTime)} / {getFormattedTime(duration)}
                    </span>
                </div>
                <div>
                    <div className='audio-player__track-bar' onClick={(e) => onTrackBarClick(e)}>
                        <div style={{ width: getFilledBarWidth() + 'px'}} className='audio-player__track-bar_filled'/>
                    </div>
                </div>
            </div>

            <audio ref={onAudioChange}>
                <source src={props.src} type="audio/mpeg"/>
                Your browser does not support the audio element.
            </audio>
        </div>
    );
}

// Audio instance storage
class AudioPlayerInstance {
    private static audioPlayer: HTMLAudioElement | undefined;
    private static onAudioPlayerChange: Function | undefined;

    private static setNewAudioPlayer(newAudioPlayer: HTMLAudioElement, onAudioPlayerChange?: Function) {
        if (AudioPlayerInstance.audioPlayer) {
            !AudioPlayerInstance.audioPlayer.paused && AudioPlayerInstance.audioPlayer.pause();
            AudioPlayerInstance.audioPlayer.currentTime = 0;
            AudioPlayerInstance.onAudioPlayerChange && AudioPlayerInstance.onAudioPlayerChange();
        }
        AudioPlayerInstance.audioPlayer = newAudioPlayer;
        AudioPlayerInstance.onAudioPlayerChange = onAudioPlayerChange;
        AudioPlayerInstance.audioPlayer.play();
    }

    public static onPlayBtnClick(audioPlayer: HTMLAudioElement | undefined, onAudioPlayerChange?: Function) {
        if (!audioPlayer) return ;
        if (AudioPlayerInstance.audioPlayer == audioPlayer) {
            AudioPlayerInstance.audioPlayer.paused ? AudioPlayerInstance.audioPlayer.play() : AudioPlayerInstance.audioPlayer.pause();
        } else {
            AudioPlayerInstance.setNewAudioPlayer(audioPlayer, onAudioPlayerChange)
        }
    }
}

export default AudioPlayer;