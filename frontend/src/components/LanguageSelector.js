import React from 'react'
import {useTranslation} from 'react-i18next'
import { changelanguage } from '../api/ApiCalls'
const LanguageSelector=()=>{
    const{i18n}=useTranslation()
    const onchangelanguage = language=>{
        i18n.changeLanguage(language);
        changelanguage(language)
    }
    return(
        <div className="container">
      
        <img src="https://ipdata.co/flags/us.png" alt="USA Flag" onClick={()=>onchangelanguage('en')} style={{cursor:'pointer'}}></img>
        
        <img src="https://ipdata.co/flags/az.png" alt="AZ Flag" onClick={()=>onchangelanguage('az')} style={{cursor:'pointer'}}></img>
 
        </div>
    )
       
}
export default LanguageSelector;