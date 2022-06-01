import React from 'react'
import profile from '../assets/profile.png'

export const ProfileImageWithDefault=(props)=>{
    let profileImage=profile;
    const{username,className,width,height,tempImage,image}=props
    if(image){
        profileImage='images/profile/'+image;
    }


    return(

<img className={className} width={width} height={height} alt={username+'profile'} src={tempImage || profileImage}
 onError={event=>{event.target.src=profile}} />
       
        
       
    )
     
        
        
};
