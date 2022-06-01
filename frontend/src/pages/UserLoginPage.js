import React, {useEffect, useState} from 'react';
import {useTranslation} from 'react-i18next'
import {useDispatch} from 'react-redux'
import { useApiProgress } from '../shared/ApiProgress';
import {loginHandler} from '../redux/authActions'
import ButtonWithProgress from '../components/ButtonWithProgress';
const UserLoginPage=(props)=>{
const [username, setUsername]=useState()
const [password, setPassword]=useState()
const [error, setError]=useState()
const{t}=useTranslation()
const dispatch=useDispatch()
const pendingApiCall=useApiProgress('post','api/1.0/auth',true)
useEffect(()=>{

setError(undefined)
},[username,password])



const onclicklogin = async event=>{
    event.preventDefault();


    

    const creds={
        username:username,
        password:password
    };

    setError(undefined)
      
    
    
    try{ 
      await dispatch(loginHandler(creds))
       props.history.push('/')
  
     
      
    }
    catch(apierror){  
        if(apierror.response.data.message){
            setError(apierror.response.data.message)
           
        }
            
          
       
       
    }
   
}

   
    const logindisabled = username && password;
    
   
    return(
        

    <div className="container">
        <form>
        <h1 className="text-center" style={{ color: '#989' }}>{t('Login')}</h1>
    <div className="form-group">
        <label>{t('Username')}</label>
    <input className="form-control"  onChange={event=>{
        setUsername(event.target.value)
    }}  />


    </div>

<div className="form-group">
<label>{t('Password')}</label>
<input className="form-control"  type="password" onChange={event=>{
        setPassword(event.target.value)
    }}/>


</div>
<br></br>
    
   {error && <div className="alert alert-secondary" role="alert"   >
    {error}
  </div>}

 <div >


     <ButtonWithProgress disabled={!logindisabled || pendingApiCall}
            className={"btn btn-outline-primary btn-lg btn-block"}
            onClick={onclicklogin}
            pendingApiCall={pendingApiCall}
            text={t('Login')}
            />

     
 </div>
<br></br>

       
</form>
  </div>  
    )



};




export default UserLoginPage;