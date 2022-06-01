import * as ACTIONS from './Constants'
const defaultState={
    isLoggedIn:false,
    username:undefined,
    displayName:undefined,
    password:undefined,
    image:undefined
  }

const authReducer=(state={...defaultState},action)=>{
    if(action.type===ACTIONS.LOGOUT_SUCCESS){
        return defaultState;
    }
    else if(action.type===ACTIONS.LOGIN_SUCCESS){
        return{
            ...action.payload,
            isLoggedIn:true
        }
    }
    else if(action.type===ACTIONS.UPDATE_SUCCESS){
        return{
            ...state,
            displayName:action.payload.displayName,
            image:action.payload.image
           
        }
    }
    return state;

}
export default authReducer