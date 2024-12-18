import { useState } from "react";
import { BusinessDTO, LoginResponseDTO } from "../../../data/Responses";
import { putBusinessAPI } from "../../../data/APICalls";
import CardComponent from "../../CardComponent";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";

interface Param {
  business: BusinessDTO | null
  onClick: () => void
}

function BusinessEditBox({business, onClick}: Param) {
  const nav = useNavigate();

  const [email, setEmail] = useState(business?.emailAddress ?? "");
  const [phone, setPhone] = useState(business?.phoneNumber ?? "");
  const [address, setAddress] = useState(business?.address ?? "");

  const onAccept = async () => {
    if(business !== null) {
      business.emailAddress = email;
      business.phoneNumber = phone;
      business.address = address;
      const loginString = localStorage.getItem("loginToken");
      if(!loginString) {
        nav("/");
        return;
      }
      const loginToken: LoginResponseDTO = JSON.parse(loginString); 
      try {
        business = await putBusinessAPI(business, loginToken);
      } catch (err) {
        if(err instanceof MissingAuthError) {
          nav("/");
          return;
        } else 
          throw err;
      }
      onClick();
    }
  }

  return <>
    <CardComponent className="businessInfo">
      <p>Business Name: {business? business.name : "Business not available"}</p>
      <input value={email} onChange={(event) => {setEmail(event.target.value)}} type="text" className="form-control" placeholder="Email"/>
      <input value={phone} onChange={(event) => {setPhone(event.target.value)}} type="text" className="form-control" placeholder="Phone"/>
      <input value={address} onChange={(event) => {setAddress(event.target.value)}} type="text" className="form-control" placeholder="Address"/>
      <button type="button" onClick={onAccept} className="btn btn-success" disabled={!Boolean(business)}>Submit</button>
      <button type="button" onClick={onClick} className="btn btn-danger" disabled={!Boolean(business)}>Cancel</button>
    </CardComponent>
  </>;
}

export default BusinessEditBox;