import { applyMiddleware, compose, createStore } from "redux";
import reducers from './reducers';
import thunk from 'redux-thunk';
import * as actions from './actions';


// For development (Should be removed in prod)
const composeEnhancers = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const defaultStore = {};

const store = createStore(reducers, defaultStore, composeEnhancers(applyMiddleware(thunk)));

const handleStoreChange = () => store;

store.subscribe(handleStoreChange);

export { actions };

export default store;