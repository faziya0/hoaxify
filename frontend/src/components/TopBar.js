import React, { useState } from 'react'
import {useRef} from 'react'
import {useEffect} from 'react'
import {useTranslation} from 'react-i18next'
import {useDispatch,useSelector} from 'react-redux'
import {Link} from 'react-router-dom'
import {logoutSuccess} from '../redux/authActions'
import hoaxify from '../assets/hoaxify.png'
import {ProfileImageWithDefault} from '../components/ProfileImageWithDefault'

const TopBar=()=>{
    const { username, isLoggedIn, displayName, image } = useSelector(store => ({
        isLoggedIn: store.isLoggedIn,
        username: store.username,
        displayName: store.displayName,
        image: store.image
      }));
    const{t}=useTranslation();
    const dispatch=useDispatch();
    const [menuVisible,setMenuVisible]=useState(false)
   

        const action=logoutSuccess();
        const logout=()=>{
           dispatch(action);
        }
        const menuArea=useRef(null);

        useEffect(()=>{
            document.addEventListener('click',menuClickTracker);
            return ()=>{
                document.removeEventListener('click',menuClickTracker)
            }
        },[isLoggedIn])
        const menuClickTracker = event =>{
            if(menuArea.current===null || !menuArea.current.contains(event.target))
            {setMenuVisible(false)}
        }
        let dropDownClassName='dropdown-menu p-0 shadow';
        if(menuVisible){
            dropDownClassName += ' show'
        }
        return(
            
           
                    <div className="  bg-light shadow-sm mb-2" style={{ backgroundColor: '989' }}>
                    <nav className="navbar navbar-light container navbar-expand" >
                    
                        
                        <Link className="navbar-brand" to="/" >
                            <img width="60" height="34" alt="hoaxify" src={hoaxify}/>
                            Hoaxify
                        </Link>
                        
                        {
                         
                        isLoggedIn?
                         <ul className="navbar-nav ms-auto" ref={menuArea}>
                             <li className="nav-item dropdown">
                            <div className="d-flex" style={{cursor:'pointer'}} onClick={()=>{setMenuVisible(true)}}>
                                <ProfileImageWithDefault image={image} width="32" height="32" className="rounded-circle"/>
                                <span className="nav-link dropdown-toggle">{displayName}</span>
                            </div>
                            <div className={dropDownClassName}>

                            <Link className="dropdown-item d-flex p-2" to={'/user/'+username} onClick={() => setMenuVisible(false)} >
                                <i className="material-icons text-info mr-4">person</i>
                                {t('My Profile')}</Link>
                        <span className="dropdown-item d-flex p-2" style={{cursor:'pointer'}} onClick={logout} >
                        <i className="material-icons text-danger mr-4">power_settings_new</i>
                            {t('Log out')}</span>
                            </div>
                        </li>
                        </ul> : 
                       
                        <ul className="navbar-nav ms-auto">
                        <li>
                        <Link className="nav-link " to="/login" >{t('Login')}</Link>
                        </li>
                        <li>
                        <Link className="nav-link " to="/signup" >{t('Sign up')}</Link>
                        </li>
 
                        </ul>
                        }
                        
                     
                   </nav>
                     </div>
                
             
            
               

            



        );
    
}




export default TopBar;