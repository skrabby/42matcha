import React from 'react';
import AudioPlayer from "../AudioPlayer";

import './ImgPreview.scss';

interface ImgPreviewProps {
    imgPreviewSrc: any;
    src?: string;
    artist?: string;
    name?: string;
}

const ImgPreview: React.FC<ImgPreviewProps> = (props) => {
    return (
        <div className='sample'>
            <img src={props.imgPreviewSrc} alt=''/>
            {/*<div className='sample__audio-player'>*/}
            {/*    <AudioPlayer artist={props.artist} name={props.name} src={props.src ?? ''}/>*/}
            {/*</div>*/}
        </div>
    );
}

export default ImgPreview;