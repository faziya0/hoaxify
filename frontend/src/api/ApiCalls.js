import axios from 'axios'
 export const signup= (body)=>{
     return axios.post('api/1.0/users',body)

}

export const changelanguage=language=>{
    axios.defaults.headers['accept-language']=language;
} 

export const loginuser=creds=>{
return axios.post('api/1.0/auth',creds)
}

export const getUsers=(pagesize=0,itemsize=3)=>{
    return axios.get(`api/1.0/users?page=${pagesize}&size=${itemsize}`)
    
}

export const setAuthorization=({isLoggedIn,token})=>{
    const authorizationHeaderValue= `Bearer ${token}`
    if(isLoggedIn){
        return axios.defaults.headers['Authorization']=authorizationHeaderValue}
    else{
        delete axios.defaults.headers['Authorization']
    }
}
export const getUser=(username)=>{
    return axios.get(`api/1.0/users/${username}`)
}

export const updateUser=(username,body)=>{
    return axios.put(`api/1.0/users/${username}`,body)
}
export const postHoax=(body)=>{
    return axios.post('api/1.0/hoaxes',body)
}
export const getHoaxes=(username,page=0)=>{
    const path=username ? `api/1.0/users/${username}/hoaxes?page=` :'api/1.0/hoaxes?page='
    return axios.get(path+page)
}

export const getOldHoaxes=(username,id)=>{
    const path = username ? `api/1.0/users/${username}/hoaxes/${id}`:`api/1.0/hoaxes/${id}`
    return axios.get(path)
}

export const getNewHoaxCount=(username,id)=>{
    const path=username ? `api/1.0/users/${username}/hoaxes/${id}/?count=true`:`api/1.0/hoaxes/${id}/?count=true`
    return axios.get(path)

}
export const getNewHoaxes=(username,id)=>{
    const path = username ? `api/1.0/users/${username}/hoaxes/${id}/?direction=after` : `api/1.0/hoaxes/${id}/?direction=after`
    return axios.get(path);

}

export const postHoaxAttachment=(body)=>{
    return axios.post('api/1.0/hoax-attachments',body)
}

export const deleteHoax=(id)=>{
    return axios.delete(`api/1.0/hoaxes/${id}`)
}
export const deleteAccount=(username)=>{
return axios.delete('api/1.0/users/'+username)
}

export const logout=()=>{
    return axios.post('api/1.0/logout')
}