import React, {useState} from "react";

import './Header.scss';
import LoginPage from "../../pages/LoginPage/LoginPage";
import {RouteComponentProps, withRouter} from "react-router";
import * as Routes from "../Router/constants";

type HeaderProps = RouteComponentProps & {
	match: {
		params: {
			profileId: string;
		}
	}
}

const Header: React.FC<HeaderProps> = (props) => {
	const [isLoginModalActive, setIsLoginModalActive] = useState<boolean>(false);

	const onLoginIconClick = () => {
		if (!localStorage.getItem("token") || !localStorage.getItem("profileId")) {
			setIsLoginModalActive(true)
		} else {
			props.history.push(Routes.SET_PROFILE(localStorage.getItem("profileId")!))
		}
	}

	const onLogoutIconClick = () => {
		localStorage.removeItem("token");
		localStorage.removeItem("id");
		props.history.push(Routes.HOME);
	}

	const onSearchIconClick = () => {
		props.history.push(Routes.SEARCH);
	}

	return (
		<header className={'header'}>
			<div className={`modal-login${isLoginModalActive ? '' : ' modal-login_disabled'}`}>
				<div className='modal-login__mask'
					 onClick={() => setIsLoginModalActive(false)}>
					<span className='modal-login__close-btn'/>
				</div>
				<LoginPage/>
			</div>
			<nav className='header__nav'>
				<div className='header__container'>
					<div/>
					<div className='header__logo' onClick={() => props.history.push(Routes.HOME)}>42MATCHA</div>
					<div>
						<ul>
							<li><a onClick={onLoginIconClick}><span className='icon__sm icon-profile'/></a></li>
							<li><a onClick={onSearchIconClick}><span className='icon__sm icon-search'/></a></li>
							<li><a onClick={onLogoutIconClick}><span className='icon__sm icon-logout'/></a></li>
						</ul>
					</div>
				</div>
			</nav>
		</header>
	)
}

export default withRouter(Header);
