import { useTranslation } from "react-i18next"
import { useSelector } from "react-redux"
import { useState } from "react"
import { useApiProgress } from "../shared/ApiProgress"
import { Link } from "react-router-dom"
import {format} from 'timeago.js'
import { deleteHoax } from "../api/ApiCalls"
import Modal from './Modal'
import { ProfileImageWithDefault } from "./ProfileImageWithDefault"


const HoaxView=(props)=>{
    const loggedInUser= useSelector(store=>store.username)
    const{hoax,onDeleteHoax}=props
    const[modalVisible,setModalVisible]=useState(false);
    const{user,content,timestap,fileAttachment,id}=hoax
    const{displayName,username,image}=user
    const{i18n,t}=useTranslation();
    const formatted=format(timestap,i18n.language)
    const ownedByLoggedInUser=loggedInUser===username
    const pendingApiCall=useApiProgress('delete','api/1.0/hoaxes/'+id,true)

    const OnClickDelete=async ()=>{
       await deleteHoax(id);
       onDeleteHoax(id);
       setModalVisible(false)
    }
    

    const OnClickCancel=()=>{
        setModalVisible(false)
    }
    return(
        <>
        <div className="card p-1">
            <div className="d-flex"> 
            <ProfileImageWithDefault image={image} width="32" height="32" className="rounded-circle"/>
            
             <div className="flex-fill ps-3">
                 <Link to={'/user/'+username}>
              {displayName}@{username}
              <span> - </span> 
              {formatted}

                 </Link>
                 </div> 
                { ownedByLoggedInUser && 
                 <button className="btn btn-delete-link btn-sm" onClick={() => setModalVisible(true)}>
                 <i className="material-icons">delete_outline</i>
               </button>
                 }
            </div>
          
        <div className="ps-5">{content}</div>
        <div className="ps-5">
        {fileAttachment && (
            <div >
                {fileAttachment.fileType && fileAttachment.fileType.startsWith('image') && (<div >
                <img className="img-fluid" src={'images/attachments/'+fileAttachment.name} alt={content} />
                </div>)
                }

            
            </div>

        )}
        </div>
        <div>
          {fileAttachment && (<div>
            {fileAttachment.fileType && !fileAttachment.fileType.startsWith('image') && (<strong>{t('Hoax has unknown attachment')}</strong>)
                }  
          </div>)}  
        </div>


        
        </div>
        <Modal
        visible={modalVisible} 
        cancel={OnClickCancel} 
        message={
        <div>
           <div> <strong>{t('Are you sure to delete hoax?')}</strong> </div>
            <span >{content}</span>
            </div>
            }
            confirm={OnClickDelete}
            pendingApiCall={pendingApiCall}
            modalTitle={t('Delete Hoax')}
            closeButton={t('Close')}
            confirmButton={t('Delete Hoax')}
            />
        </>
        
    )
}
export default HoaxView