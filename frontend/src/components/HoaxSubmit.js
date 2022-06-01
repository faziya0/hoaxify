
import { useSelector } from "react-redux"
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useApiProgress } from "../shared/ApiProgress";
import { postHoax,postHoaxAttachment} from "../api/ApiCalls";
import AutoUploadImage from "./AutoUploadImage";
import ButtonWithProgress from "./ButtonWithProgress";
import { ProfileImageWithDefault } from "./ProfileImageWithDefault"
const HoaxSubmit=()=>{
    const { image } = useSelector(store => ({
        image: store.image
      }));
    const[attachmentId,setAttachmentId]=useState();
    const[newImage,setNewImage]=useState();
    const[focused,setFocused] = useState(false);
    const[hoax,setHoax]=useState('');
    const[validationErrors,setValidationErrors]=useState({});
    const{t} = useTranslation();
  
  useEffect(()=>{
   if(!focused){
       setHoax('');
       setValidationErrors({});
       setNewImage();
       setAttachmentId();
   }
  },[focused]);
  useEffect(()=>{
  setValidationErrors({});
  },[hoax]);

  const pendingApiCall=useApiProgress('post','api/1.0/hoaxes',true);
  const pendingFileUpload=useApiProgress('post','api/1.0/hoax-attachments',true);
  const onClickHoaxify= async ()=>{
    setValidationErrors({})
      const body={
          content:hoax,
          attachmentId:attachmentId
      };
     try{
     await postHoax(body);
     setFocused(false);
    } 
     catch(error){
         
     setValidationErrors(error.response.data.validationErrors)
        
     }

  }

  const  onChangeFile=(event)=>{
        
    if(event.target.files.length<1){
        return;
    }
    const file=event.target.files[0];
    const fileReader=new FileReader();
    fileReader.onloadend=()=>{
     setNewImage(fileReader.result)
     uploadFile(file);
    }
    fileReader.readAsDataURL(file)
  };

  const uploadFile = async (file)=>{
   const attachment = new FormData();
   attachment.append('image',file);
  const response= await postHoaxAttachment(attachment);
  setAttachmentId(response.data.id)
  };

  const{content:contentError}=validationErrors
   return(
       <div className="card p-1 flex-row">
           <ProfileImageWithDefault image={image} width="32" height="32" className="rounded-circle"/>
           <div className="flex-fill">
           <textarea className={contentError ? 'form-control is-invalid' :'form-control ms-1'} rows={focused ? "3":"1" } 
           onFocus={()=>{setFocused(true)}}
           onChange={(event)=>{setHoax(event.target.value)}}
           value={hoax}

           />
           <div className="invalid-feedback">{contentError}</div>
           {focused &&<div>
            {newImage && <AutoUploadImage image={newImage} uploadApiCall={pendingFileUpload}/>}
           {!newImage && <input className="mt-2" type="file" onChange={onChangeFile}/>} 
            
               <div className="text-end">
           
           <ButtonWithProgress disabled={pendingApiCall || pendingFileUpload}
            className={"btn btn-primary mt-1 me-2"}
            onClick={onClickHoaxify}
            pendingApiCall={pendingApiCall}
            text={t('Hoaxify')}
            />
            
            <button className="btn btn-light d-inline-flex ml-1" onClick={() => setFocused(false)} disabled={pendingApiCall || pendingFileUpload}>
                <i className="material-icons">close</i>
                {t('Cancel')}
              </button>
            
           </div>
           </div>}
           
           </div>
           
       </div>
   ) 
}
export default HoaxSubmit