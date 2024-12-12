interface Param {
  placeholder: string
}

function InputTextField({placeholder}: Param) {
  return <>
    <div className="input-group mb-3">
      <input type="text" className="form-control" placeholder={placeholder}/>
    </div>
  </>;

}

export default InputTextField;