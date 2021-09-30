import React from "react";
import { Link } from 'react-router-dom';

import './Header.scss';

interface HeaderProps {
	onProfileClick?: () => void;
}

const Header: React.FC<HeaderProps> = (props) => {


	return (
		<header>
			<div className='header__msg'>
				This website is working in trial mode
			</div>
			<nav className='header__nav'>
				<div className='header__container'>
					<div/>
					<div>Selfpiano</div>
					<div>
						<ul>
							<li><a onClick={() => props.onProfileClick && props.onProfileClick()}><span className='icon__sm icon-profile'/></a></li>
							<li><span className='icon__sm icon-search'/></li>
							<li><Link to='/'><span className='icon__sm icon-shopping-bag'/></Link></li>
						</ul>
					</div>
				</div>
			</nav>
		</header>
	)
}

export default Header;