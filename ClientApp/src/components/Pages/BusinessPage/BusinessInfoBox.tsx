import { BusinessDTO } from "../../../data/Responses";
import CardComponent from "../../CardComponent";

interface Param {
  business: BusinessDTO | null
  onClick: () => void
}

function BusinessInfoBox({business, onClick}: Param) {
  return <>
    <CardComponent className="businessInfo">
      <p>Business Name: {business? business.name : "Business not available"}</p>
      <p>Business Email: {business? business.emailAddress : "Business not available"}</p>
      <p>Business Phone: {business? business.phoneNumber : "Business not available"}</p>
      <p>Business Address: {business? business.address : "Business not available"}</p>
      <button type="button" onClick={onClick} className="btn btn-primary" disabled={!Boolean(business)}>Edit</button>
    </CardComponent>
  </>;
}

export default BusinessInfoBox;