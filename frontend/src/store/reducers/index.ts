import { combineReducers } from 'redux';

import reducer from './reducer';
import loadersReducer from "./loaderReducer";

export default combineReducers({
    reducer,
    loadersReducer
});