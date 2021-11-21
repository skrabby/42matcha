import React from 'react';
import { Switch, Route } from 'react-router-dom';

import * as Pages from '../../pages';
import * as Routes from './constants';

export default class Router extends React.Component {
    render() {
        return (
            <Switch>
                <Route exact path={Routes.HOME} component={Pages.HomePage} />
                <Route exact path={Routes.LOGIN} component={Pages.LoginPage} />
                <Route exact path={Routes.PROFILE} component={Pages.ProfilePage} />
                <Route exact path={Routes.CHATS} component={Pages.ChatsPage} />
                <Route exact path={Routes.CHAT} component={Pages.ChatPage} />
            </Switch>
        );
    }
}
