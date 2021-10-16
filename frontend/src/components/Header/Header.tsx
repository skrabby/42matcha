import React, {useState} from "react";
import { Link } from 'react-router-dom';

import './Header.scss';
import LoginPage from "../../pages/LoginPage/LoginPage";

interface HeaderProps {}

const Header: React.FC<HeaderProps> = (props) => {
	const [isLoginModalActive, setIsLoginModalActive] = useState<boolean>(false);

	return (
		<header className={'header'}>
			<div className={`modal-login${isLoginModalActive ? '' : ' modal-login_disabled'}`}>
				<div className='modal-login__mask'
					 onClick={() => setIsLoginModalActive(false)}>
					<span className='modal-login__close-btn'/>
				</div>
				<LoginPage/>
			</div>
			{/*<div className='header__msg'>*/}
			{/*	LOVE*/}
			{/*</div>*/}
			<nav className='header__nav'>
				<div className='header__container'>
					<div/>
					<div>42MATCHA</div>
					<div>
						<ul>
							<li><a onClick={() => setIsLoginModalActive(true)}><span className='icon__sm icon-profile'/></a></li>
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