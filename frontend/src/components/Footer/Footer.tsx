import React from "react";

import './Footer.scss';
import followUsImg from "../../assets/img/follow.png";

const Footer: React.FC = (props) => {
	return (
		<footer>
			<div className='follow-us'>
				<h1 className='follow-us__title'>Follow us on youtube</h1>
				<p className='follow-us__subtitle'>New videos everyday. Stay tuned!</p>
				<span className='icon-youtube follow-us__icon'></span>
				<img src={followUsImg}/>
			</div>
			<div className='info'>
				<div>
					<p>ductruong1802@gmail.com</p>
					<p>Copyright © 2021, Duc Truong</p>
				</div>
			</div>
		</footer>
	)
}

export default Footer;