import  React,{useState,useEffect} from 'react';
import { useTranslation } from 'react-i18next';
import { useApiProgress } from '../shared/ApiProgress';
import { getUser } from '../api/ApiCalls';
import HoaxFeed from '../components/HoaxFeed'
import ProfileCard from '../components/ProfileCard';
import Spinner from '../components/Spinner';

const UserPage = (props)=>{
    const{username}=props.match.params
    const[user,setUser]=useState({});
    const[notFound,setNotFound]=useState(false);
    const{t}=useTranslation();
    const pendingApiCall=useApiProgress('get',`api/0.1/users/${username}`,true);
    useEffect(()=>{
       setNotFound(false) 
       
    },[user])
    useEffect(()=>{
        const loadUser= async ()=>{
            try{
                const response=await getUser(username)
                
                setUser(response.data)
            }
            catch(error){
        setNotFound(true)
            }
        
    
        }
        loadUser();
    },[username])

    

    if(notFound){
        return(
            <div className="container">
                <div className="alert alert-danger text-center" >
                    <div>
                    <span className="material-icons">
                      error_outline
                       </span> 
                    </div>
                    <h6>{t('User can not found!')}</h6>
               
              
              </div>
                </div>
        )
    }
    if(pendingApiCall || user.username!==username){
        return(
            <div className="d-flex justify-content-center">
  <Spinner/>
</div>
        )
    }
    


    return(
        <div className="row">
        <div className="col">
        <HoaxFeed/>
        </div>
        <div className="col">
            <ProfileCard user={user} />
    
        </div>
        </div>
    )
}

export default UserPage;
