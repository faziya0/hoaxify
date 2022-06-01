import {createStore,applyMiddleware} from 'redux'
import authReducer from './authReducer'
import SecureLs from 'secure-ls'
import thunk from 'redux-thunk'
import { setAuthorization } from '../api/ApiCalls';

  const secureLs=new SecureLs();

  const getStateFromStorage=()=>{
    let stateInLocalStore={
      isLoggedIn:false,
      username:undefined,
      displayName:undefined,
      password:undefined,
      image:undefined
    }
    const hoaxauth=secureLs.get('hoax-auth');
    if(hoaxauth){
     stateInLocalStore=hoaxauth;
      }
      return stateInLocalStore;
  }
  const updateStateInLocalStore=(newSate)=>{
   secureLs.set('hoax-auth',newSate)
  }

  const configureStore=()=>{
    const initial=getStateFromStorage();
    setAuthorization(initial);
    const store=createStore(authReducer,initial,applyMiddleware(thunk));
    store.subscribe(()=>{
        updateStateInLocalStore(store.getState())
        setAuthorization(store.getState())
    })
    return store;

  }

  export default configureStore;