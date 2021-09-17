import * as Constants from '../constants';
import * as Interfaces from '../interfaces';

interface IReducerReturnValue {
	[key: string]: any;
}

export default function reducer(state = {}, action: Interfaces.IAction): IReducerReturnValue {
	switch(action.type) {
		case(Constants.SET_PRODUCTS):
			return { ...state, products: action.payload}
		default:
			break ;
	}
	return {...state};
}