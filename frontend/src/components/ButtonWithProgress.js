import Spinner from "./Spinner"
const ButtonWithProgress=(props)=>{
    const{pendingApiCall,onClick,disabled,text,className}=props
    return(
        <button className={className || "btn btn-primary"} onClick={onClick}  disabled={disabled}>
        {text}  {pendingApiCall && <Spinner/> } 
        </button>
    )

}
export default ButtonWithProgress