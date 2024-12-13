import { useRef, useState } from "react"
import "./login.css"
import validator from "validator"
import { useNavigate } from "react-router";
import { getUserAPI } from "../../../data/APICalls";
import { getUserLink } from "../../../data/Routes";

function Login() {
  const [pVisible, setPVisible] = useState(false);
  const inpRef = useRef<HTMLInputElement>(null);
  const textRef = useRef<HTMLParagraphElement>(null);
  const nav = useNavigate();

  const onClick = async () => {
    if(inpRef.current && textRef.current){
      const text = inpRef.current.value;
      if(!text) {
        textRef.current.innerText = "This field cannot be empty!";
        setPVisible(true);
      } else if(!validator.isUUID(text)) {
        textRef.current.innerText = "Invalid username!"
        setPVisible(true);
      } else {
        const user = await getUserAPI(text);
        if(user) {
          localStorage.setItem("userId", user.id);
          localStorage.setItem("userBusinessId", user.businessId);
          setPVisible(false);
          nav("/home");
        }
      }
    }
  };

  return <>
    <div className="loginPage">
      <h1 className="loginH">Log in</h1>
      <input ref={inpRef} type="text" className="form-control" placeholder="Enter your ID"/>
      <p ref={textRef} style={{visibility: pVisible ? 'visible' : 'hidden'}} className="small"></p>
      <button type="button" onClick={onClick} className="btn btn-primary">Log in</button>
    </div>
  </>;
}

export default Login;