import {useState,useEffect} from 'react'
import { useParams } from 'react-router'
import { useApiProgress } from '../shared/ApiProgress'
import { useTranslation } from 'react-i18next'
import { getHoaxes,getOldHoaxes,getNewHoaxes } from '../api/ApiCalls'
import { getNewHoaxCount } from '../api/ApiCalls'
import HoaxView from './HoaxView'
import Spinner from './Spinner'


const HoaxFeed=()=>{
const[hoaxPage,setHoaxPage]=useState({content:[],last:true,number:0})
const [newHoaxCount,setNewHoaxCount]=useState();
const{t}=useTranslation();
const{username}=useParams();
const path=username ? `api/1.0/users/${username}/hoaxes?page=` :'api/1.0/hoaxes?page=';
const initialHoaxLoadProgress =useApiProgress('get',path);
let lastHoaxId=0
let firstHoaxId=0
if(hoaxPage.content.length>0){
    const lastIndex=hoaxPage.content.length-1;
   lastHoaxId= hoaxPage.content[lastIndex].id;
   firstHoaxId=hoaxPage.content[firstHoaxId].id;
}
const OldHoaxpath=username ? `api/1.0/users/${username}/hoaxes/${lastHoaxId}` :`api/1.0/hoaxes/${lastHoaxId}`;
const loadOldHoaxesProgress =useApiProgress('get', OldHoaxpath,true);
const NewHoaxPath= username ? `api/1.0/users/${username}/hoaxes/${firstHoaxId}/?direction=after` : `api/1.0/hoaxes/${firstHoaxId}/?direction=after`
const loadNewHoaxesProgress=useApiProgress('get',NewHoaxPath,true)
useEffect(()=>{
    const getCount=async ()=>{
   const response= await getNewHoaxCount(username,firstHoaxId);
   setNewHoaxCount(response.data.count);
   
    };
    let loop=setInterval(getCount,1000)
    return function cleanup(){
      clearInterval(loop);
    }
},[firstHoaxId,username])
useEffect(()=>{
    const loadHoaxes= async ()=>{
        try{
            const response=await getHoaxes(username);
            setHoaxPage(previous=>({
                ...response.data,
                content:[...previous.content, ...response.data.content]
            }))
          
        }
        catch(error){
    
        }
      };
    loadHoaxes();
    
},[username])

  const loadOldHoaxes= async()=>{
    const lastIndex=hoaxPage.content.length-1;
   const id= hoaxPage.content[lastIndex].id;
   try{const response = await getOldHoaxes(username,id);
    setHoaxPage(previous=>({
     ...response.data,
     content:[...previous.content, ...response.data.content]
 }))}
 catch(error){}
   

  }
  const loadNewHoaxes=async ()=>{
        const response= await getNewHoaxes(username,firstHoaxId);
        setHoaxPage(previous=>({
          ...previous,
          content:[...response.data, ...previous.content]
      }))
      setNewHoaxCount(0);

  }
  const deleteHoaxSuccess=(id)=>{
    setHoaxPage(previous=>({
      ...previous,
      content:previous.content.filter((hoax)=>{
      if(hoax.id!==id){
        return true;
      }
      return false;
      })
  }))
  }
const{content,last}=hoaxPage

if(content.length===0){
  return(
      <div className="alert alert-secondary text-center">{initialHoaxLoadProgress ? 
          <Spinner/>:  t('There is no hoax')}  </div>
          
          
  )
}

    return(
        <div>
   { newHoaxCount>0 &&(
   <div
    className="alert alert-secondary text-center"
    style={{cursor : loadNewHoaxesProgress ? "not-allowed":"pointer"}}
    onClick={loadNewHoaxesProgress ? ()=>{}: ()=>{loadNewHoaxes()}}>
      {loadNewHoaxesProgress ?  <Spinner/> : t('Load new hoaxes')}
      </div>)}

    
    {content.map(hoax=>(<HoaxView key={hoax.id} hoax={hoax} onDeleteHoax={deleteHoaxSuccess}/>))}
    {!last &&  <div className="alert alert-secondary text-center" 
    style={{cursor:loadOldHoaxesProgress ? 'not-allowed':'pointer'}}
    onClick={loadOldHoaxesProgress ? ()=>{}:()=>{loadOldHoaxes()}}>
    {loadOldHoaxesProgress ?  <Spinner/> : t('Load old hoaxes')}
        
        
        </div>}
        </div>
        
    )


}
export default HoaxFeed