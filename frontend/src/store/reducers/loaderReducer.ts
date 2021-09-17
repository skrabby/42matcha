import * as Constants from '../constants';
import * as Interfaces from '../interfaces';

interface IReducerReturnValue {
    [key: string]: any;
}

export default function loadersReducer(state = {}, action: Interfaces.IAction): IReducerReturnValue {
    switch(action.type) {
        case(Constants.SET_MAIN_LOADER):
            return { ...state, isMainPageLoaderActive: action.payload}
        default:
            break ;
    }
    return {...state};
}