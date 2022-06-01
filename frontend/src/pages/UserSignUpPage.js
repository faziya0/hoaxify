import React, {useState } from 'react'
import {useTranslation} from 'react-i18next'
import {useDispatch} from 'react-redux'
import { useApiProgress } from '../shared/ApiProgress'
import {signupHandler} from '../redux/authActions'
import ButtonWithProgress from '../components/ButtonWithProgress'
 const UserSignUpPage=(props)=>{
   
   const[form,setForm]=useState({
    username:null,
     displayName:null,
     password:null,
     passwordRepeat:null
   })
     const[errors,setErrors]=useState({})
     const{t}=useTranslation()
     const dispatch=useDispatch()
     const pendingApiCallLogin=useApiProgress('post','api/0.1/auth')
     const pendingApiCallSignup=useApiProgress('post','api/0.1/users')

   
  const onchange = event =>{
    
    const {name,value} = event.target;
    const errorsCopy={...errors}
    errorsCopy[name]=undefined;
    
    if(name==='password' || name==='passwordRepeat'){
     if(name==='password' && value!==form.passwordRepeat){
      errorsCopy.passwordRepeat= t('password mismatch');
     }
     else if(name==="passwordRepeat" && value!==form.password){
      errorsCopy.passwordRepeat= t('password mismatch');
     }
     else{
       errorsCopy.passwordRepeat=undefined;
     }
    }
   
   setErrors(errorsCopy)
   const formCopy={...form}
   formCopy[name]=value
   setForm(formCopy)
    };
    

    const onclicksignup = async event =>{
        event.preventDefault();
        
        
  const{username,displayName,password}=form
        const body = {
           username,
           displayName,
           password
        };
        
    try{
      
      const{history}=props;
      const{push}=history
      await dispatch(signupHandler(body))
        push('/')
    
       
    }
    catch(error){
      if(error.response.data.validationErrors){
      setErrors(error.response.data.validationErrors)
        
      }
    
    }
     
        

    };
  
   


    
      
      const pendingApiCall=pendingApiCallLogin || pendingApiCallSignup;
      const{username,displayName,password}=form
      const{passwordRepeat}=errors
      const signupdisabled=username&&displayName&&password&&(passwordRepeat===undefined)
      const {username:usernameError,displayName:displayNameError,password:passwordError,passwordRepeat:passwordRepeatError}=errors;
      
        return(
          <div className="container">
      <form>
        <h1 className="text-center" style={{ color: '#989' }}>{t('Sign Up')}</h1>

        <div className="form-group">
        <label>{t('Username')}</label>
      <input className={usernameError ? 'form-control is-invalid' : 'form-control'} name="username" onChange={onchange}/>
      <div className="invalid-feedback">
      {usernameError}
    </div>
        </div>
        <br></br>
        <div className="form-group">
        <label>{t('Display Name')}</label>
      <input className={displayNameError ? 'form-control is-invalid' : 'form-control'} name="displayName" onChange={onchange}/>
      <div className="invalid-feedback">
      {t(displayNameError)}
    </div>
        </div>
        
        
         <div className="form-group">
         <br></br>
           <label>{t('Password')}</label>
         <input className={passwordError ? 'form-control is-invalid' : 'form-control'}  name="password" type="password" onChange={onchange}/>
         <div className="invalid-feedback">
      {passwordError}
    </div>
         </div>

         <div className="form-group">
         <br></br>
           <label>{t('Password Repeat')}</label>
         <input className={passwordRepeatError ? 'form-control is-invalid' : 'form-control'} name="passwordRepeat" type="password" onChange={onchange}/>
         <div className="invalid-feedback">
      {passwordRepeatError}
    </div>
         </div>
         <br></br>
         <div >
         <ButtonWithProgress disabled={!signupdisabled || pendingApiCall}
            className={"btn btn-outline-primary btn-lg btn-block"}
            onClick={onclicksignup}
            pendingApiCall={pendingApiCall}
            text={t('Sign up')}
            />
      
       </div>
       <br></br>
       
        
      </form>
      </div>
     
        )}

export default UserSignUpPage;
