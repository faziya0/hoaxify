import ButtonWithProgress from "./ButtonWithProgress"
const Modal =(props)=>{
   
    const{visible,cancel,message,confirm,pendingApiCall,modalTitle,closeButton,confirmButton}=props
    let className='modal fade'
    if(visible){
     className='modal fade show d-block'
    }
    return(
    <div className={className} style={{backgroundColor:'#000000b0'}} >
  <div className="modal-dialog">
    <div className="modal-content">
      <div className="modal-header">
        <h5 className="modal-title" >{modalTitle}</h5>
        
      </div>
      <div className="modal-body">
        {message}
      </div>
      <div className="modal-footer">
      <ButtonWithProgress disabled={pendingApiCall}
            className={"btn btn-danger"}
            onClick={confirm}
            pendingApiCall={pendingApiCall}
            text={confirmButton}
            />
        <button type="button" className="btn btn-secondary" disabled={pendingApiCall} onClick={cancel} >{closeButton}</button>
        
      </div>
    </div>
  </div>
</div>
    );
    
}
export default Modal
