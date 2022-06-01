import * as ACTIONS from "./Constants"; 
import {loginuser,signup,logout} from '../api/ApiCalls'


export const logoutSuccess=()=>{

    return async function(dispatch){
        try{
            await logout();
        }
        catch(error){

        }
        dispatch({
            type:ACTIONS.LOGOUT_SUCCESS
        })
    }
    
};



export const loginSuccess=(authState)=>{
    return {
        type:ACTIONS.LOGIN_SUCCESS,
        payload:authState
    }
}
export const updateSuccess=({displayName,image})=>{
    return{
        type:ACTIONS.UPDATE_SUCCESS,
        payload:{displayName,image}
    }
}

export const loginHandler=(credentials)=>{
    return async function(dispatch){
        const response = await loginuser(credentials)
        const authState={
            username:response.data.user.username,
            displayName:response.data.user.displayName,
            password:credentials.password,
            image:response.data.user.image,
            token:response.data.token

        }
        
         dispatch(loginSuccess(authState)) 
        return response
    }

}

export const signupHandler=(body)=>{
    return async function(dispatch){
        const response =await signup(body)
        await dispatch(loginHandler(body));
        return response;
    }
    


}