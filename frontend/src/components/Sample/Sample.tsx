import React from 'react';
import AudioPlayer from "../AudioPlayer";

import './Sample.scss';

interface SampleProps {
    imgPreviewSrc: any;
    src?: string;
    artist?: string;
    name?: string;
}

const Sample: React.FC<SampleProps> = (props) => {
    return (
        <div className='sample'>
            <img src={props.imgPreviewSrc}/>
            <div className='sample__audio-player'>
                <AudioPlayer artist={props.artist} name={props.name} src={props.src ?? ''}/>
            </div>
        </div>
    );
}

export default Sample;