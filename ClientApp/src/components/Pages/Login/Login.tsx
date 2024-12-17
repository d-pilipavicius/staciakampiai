import { useState } from "react"
import "./login.css"
import { useNavigate } from "react-router";
import { loginAPI } from "../../../data/APICalls";
import { LoginDTO } from "../../../data/Responses";

function Login() {
  const [showPass, setShowPass] = useState(false);

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const nav = useNavigate();

  const onKeyDown = (event: React.KeyboardEvent) => {
    if(event.key == "Enter")
      onClick();
  }

  const onClick = async () => {
    if(username && password) {
      const loginInfo: LoginDTO = {
        username: username,
        password: password
      };
      const loginDetails = await loginAPI(loginInfo);
      if(loginDetails) {
        localStorage.setItem("loginToken", JSON.stringify(loginDetails));
        nav("/home");
        console.log(JSON.stringify(loginDetails));
      }
    }
  }

  localStorage.removeItem("loginToken");

  return <>
    <div className="loginPage">
      <h1 className="loginH">Log in</h1>
      <form>
        <input className="form-control" value={username} onChange={(event) => setUsername(event.target.value)} type="text" placeholder="Username"/>
        <div className="input-group">
          <input className="form-control" onKeyDown={onKeyDown} value={password} onChange={(event) => setPassword(event.target.value)} type={showPass ? "text" : "password"} placeholder="Password"/>
          <span className="input-group-text" onClick={() => setShowPass(!showPass)} style={{ cursor: 'pointer' }}><img src={showPass ? "../../eye-open.svg" : "../../eye-closed.svg"}/></span>
        </div>
        <button type="button" onClick={onClick} className="btn btn-primary">Log in</button>
      </form>
    </div>
  </>;
}

export default Login;