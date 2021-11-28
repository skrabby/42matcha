import React, {BaseSyntheticEvent } from 'react';
import * as Components from '../../components';
import * as Actions from '../../store/actions';
import { Message } from '../../utils/enums';
import * as Rules from '../../utils/rules';
import * as api from './api';
import {RouteComponentProps, withRouter} from "react-router";

import './LoginPage.scss';

export enum Card {
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD
}

type LoginPageProps = RouteComponentProps & {
    card?: Card;
}

interface LoginPageState {
    card: Card;
    statusMsgType: Message;
    statusMsg: string;
}

class LoginPage extends React.Component<LoginPageProps, LoginPageState> {

    state = {
        card: this.props.card || Card.LOGIN,
        statusMsgType: Message.NONE,
        statusMsg: '',
    }

    onRegisterClick = () => {
        this.setState({card: Card.REGISTER});
    }

    onLoginClick = () => {
        this.setState({card: Card.LOGIN});
    }

    onForgotPasswordClick = () => {
        this.setState({card: Card.FORGOT_PASSWORD});
    }

    onLoginSubmit = (e: BaseSyntheticEvent) => {
        const data: Actions.Interfaces.ILoginSubmit = {
            username: fields.login.value,
            password: fields.password.value
        }

        api.login(data)
            .then((rs: any) => {
                if (rs.status === 401) {
                    this.props.history.push('?login=fail');
                } else {
                    localStorage.setItem("token", rs.token);
                    localStorage.setItem("profileId", rs.id);
                    this.props.history.push(`/id${rs.id}`)
                }
            })
    }

    onForgotPasswordSubmit = (e: BaseSyntheticEvent) => {
        const data: Actions.Interfaces.IForgotPasswordSubmit = {
            username: fields.login.value,
        }

        //Actions.SubmitActions.submitLogin(data)
    }

    onRegisterSubmit = (e: BaseSyntheticEvent) => {
        const data: Actions.Interfaces.IRegisterSubmit = {
            username: fields.login.value,
            password: fields.password.value
        }

        //Actions.SubmitActions.submitLogin(data)
    }

    getMsgColor() {
        const { statusMsgType } = this.state;

        if (statusMsgType === Message.ERROR) {
            return 'error-msg';
        } else if (statusMsgType === Message.INFO) {
            return 'info-msg';
        }
        return '';
    }

    renderLoginCard = () => {
        return (
            <React.Fragment>
            <div className='input-block'>
                <div className={'status-msg ' + this.getMsgColor()}>
                    {this.state.statusMsgType !== Message.NONE && this.state.statusMsg}
                </div>
                <Components.Form
                    onSubmit={this.onLoginSubmit}
                    fields={
                        <React.Fragment>
                            <Components.Input {...fields.login} onChange={(e) => fields.login.value = e.value } />
                            <Components.Input {...fields.password} onChange={(e) => fields.password.value = e.value }/>
                        </React.Fragment>
                    }>
                    <div className='social-auth-block'>
                        <div className='social-icon facebook'/>
                        <div className='social-icon google'/>
                        <div className='social-icon twitter'/>
                    </div>
                    <div className='btn-submit-block'>
                        <Components.Button type="submit" className='btn btn-primary center'>Login</Components.Button>
                    </div>
                </Components.Form>
            </div>

            <div className='password-reminder'>
                <span className="link-text" onClick={() => this.onForgotPasswordClick()}>Forgot your password?</span>
            </div>
            <div className='register-text'>No account yet? <span onClick={() => this.onRegisterClick()} className='link-text'>Register</span></div>
        </React.Fragment>
        );
    }

    renderRegisterCard() {
        return (
            <React.Fragment>
                <div className='input-block'>
                    <div className={'status-msg ' + this.getMsgColor()}>
                        {this.state.statusMsgType !== Message.NONE && this.state.statusMsg}
                    </div>
                    <Components.Form
                        onSubmit={this.onRegisterSubmit}
                        fields={
                            <React.Fragment>
                                <Components.Input {...fields.login}/>
                                <Components.Input {...fields.firstName} />
                                <Components.Input {...fields.lastName} />
                                <Components.Input {...fields.regPassword} />
                                <Components.Input {...fields.regRptPassword} />
                            </React.Fragment>
                        }>
                        <div className='social-auth-block'>
                            <div className='social-icon facebook'></div>
                            <div className='social-icon google'></div>
                            <div className='social-icon twitter'></div>
                        </div>
                        <div className='btn-submit-block'>
                            <Components.Button type="submit" className='btn btn-primary center'>Register</Components.Button>
                        </div>
                    </Components.Form>
                </div>

                <div className='password-reminder'>
                    <span className="link-text" onClick={() => this.onForgotPasswordClick()}>Forgot your password?</span>
                </div>
                <div className='register-text'>Already have an account? <span onClick={() => this.onLoginClick()} className='link-text'>Login</span></div>
            </React.Fragment>
        );
    }

    renderForgotPasswordCard() {
        return (
            <React.Fragment>
                <div className='input-block'>
                    <div className={'status-msg ' + this.getMsgColor()}>
                        {this.state.statusMsgType !== Message.NONE && this.state.statusMsg}
                    </div>
                    <Components.Form
                        onSubmit={this.onForgotPasswordSubmit}
                        fields={
                            <React.Fragment>
                                <Components.Input {...fields.login} />
                            </React.Fragment>
                        }>
                        <div className='btn-submit-block'>
                            <Components.Button type="submit" className='btn btn-primary center'>Submit</Components.Button>
                        </div>
                    </Components.Form>
                </div>
                <div className='register-text'>Want to login again? <span onClick={() => this.onLoginClick()} className='link-text'>Login</span></div>
                <div className='register-text'>No account yet? <span onClick={() => this.onRegisterClick()} className='link-text'>Register</span></div>
            </React.Fragment>
        );
    }

    renderForm() {
        const { card } = this.state;

        switch(card) {
            case Card.LOGIN:
                return this.renderLoginCard();
            case Card.REGISTER:
                return this.renderRegisterCard();
            case Card.FORGOT_PASSWORD:
                return this.renderForgotPasswordCard();
        }

    }

    render() {
        return (
            <div className='wrapper-pic'>
                <div
                    key={this.state.card}
                    className='card abs-center login'>
                    {/*<div className='logo img'></div>*/}
                    <div className='logo title'>42Matcha</div>
                    {this.renderForm()}
                </div>
            </div>
        )
    }
}

export default withRouter(LoginPage);

const fields: any = {
    login: {
        id: 'login',
        className: 'input md login',
        placeholder: 'Email*',
        icon: 'email',
        rules: [{ rule: Rules.isRequired }],
    },
    password: {
        id: 'password',
        className: 'input md login',
        placeholder: 'Password*',
        icon: 'eye-open',
        type: 'password',
        rules: [{ rule: Rules.isRequired }],
    },
    regPassword: {
        id: 'regPassword',
        className: 'input md login',
        placeholder: 'Password*',
        icon: 'eye-open',
        type: 'password',
        rules: [{ rule: Rules.hasCapLetter }, { rule: Rules.hasDigit }, { rule: Rules.minLength, args: { minLength: 6 } }],
    },
    regRptPassword: {
        id: 'regRptPassword',
        className: 'input md login',
        placeholder: 'Repeat password*',
        icon: 'eye-open',
        type: 'password',
        rules: [{ rule: Rules.hasCapLetter }, { rule: Rules.hasDigit }, { rule: Rules.minLength, args: { minLength: 6 } }],
    },
    firstName: {
        id: 'firstName',
        className: 'input md login',
        placeholder: 'First name',
    },
    lastName: {
        id: 'lastName',
        className: 'input md login',
        placeholder: 'Last name',
    }
}
