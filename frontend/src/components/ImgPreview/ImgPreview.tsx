import React from 'react';

import './ImgPreview.scss';

interface ImgPreviewProps {
    imgPreviewSrc: any;
    src?: string;
    artist?: string;
    name?: string;
    onChange?: (e: any) => void;
}

const ImgPreview: React.FC<ImgPreviewProps> = (props) => {
    return (
        <div className='sample'>
            <label>
                <img src={props.imgPreviewSrc} alt=''/>
                <input
                    type="file"
                    accept="image/png, image/gif, image/jpeg"
                    style={{display:"none"}}
                    onChange={(e) => props.onChange && props.onChange(e)}
                />
            </label>
        </div>
    );
}

export default ImgPreview;
