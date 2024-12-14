import { useState } from "react";
import { BusinessDTO } from "../../../data/Responses";
import { deleteProductModifierAPI, putBusinessAPI } from "../../../data/APICalls";
import CardComponent from "../../CardComponent";

interface Param {
  business: BusinessDTO | null
  onClick: () => void
}

function BusinessEditBox({business, onClick}: Param) {
  const [email, setEmail] = useState(business?.emailAddress ?? "");
  const [phone, setPhone] = useState(business?.phoneNumber ?? "");
  const [address, setAddress] = useState(business?.address ?? "");

  const onAccept = async () => {
    if(business !== null) {
      business.emailAddress = email;
      business.phoneNumber = phone;
      business.address = address;
      await putBusinessAPI(business);
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