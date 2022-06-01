import { useState,useEffect } from 'react'
import {useTranslation} from 'react-i18next'
import { useApiProgress } from '../shared/ApiProgress'
import {getUsers} from '../api/ApiCalls'
import {UserListItem} from '../components/UserListItem'
import Spinner from './Spinner'
const UserList=()=>{
    const[page, setPage]=useState({
        content:[],
        size:3,
        number:0
    })

  
 const pendingApiCall=useApiProgress('get','api/1.0/users?page')
    useEffect(()=>{
       loadUsers();

    },[])
    


const onClickNext=()=>{
    const nextPage=page.number+1;
    getUsers(nextPage).then(response=>{
        setPage(response.data)
        
    })

}
const onClickPrevious=()=>{
const previousPage=page.number-1;
getUsers(previousPage).then(response=>{
    setPage(response.data)
})
}
const loadUsers = async page => {
    try {
      const response = await getUsers(page);
      setPage(response.data);
    } catch (error) {
      
    }
  };


        const{content}=page;
        const{t}=useTranslation();
        const{last,first}=page
       
        
        return(
            <div className="card">
                        <h3 className="card-header text-center">{t('Users')}</h3>
                     
                        <div className="list-group-flush">
                        {content.map(user=>(
              <UserListItem key={user.username} user={user}/>
        ))} </div>
        {pendingApiCall ? <Spinner/> : 
        <div>{first===false && (<button className="btn btn-sm btn-light" onClick={onClickPrevious}>
        {t('Previous')}
      </button>
            )
        }
        {last===false && (
        <button className="btn btn-sm btn-light float-end" onClick={onClickNext}>
        {t('Next')}
      </button>
        )}
        </div>}

        
        
    
     
                      
                    </div>
          
        )
    
}
export default UserList;