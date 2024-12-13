import { useEffect, useState } from "react";
import Header from "../../Header";
import { getBusinessAPI, getMyBusiness } from "../../../data/APICalls";
import { BusinessDTO } from "../../../data/Responses";
import BusinessInfoBox from "./BusinessInfoBox";
import BusinessEditBox from "./BusinessEditBox";

function BusinessPage() {
  const [isEditing, setEditing] = useState(false);
  const [business, setBusiness] = useState<BusinessDTO | null>(null);

  useEffect(() => {
    if(isEditing)
      return;
    const fetchData = async (id: string) => {
      const businessdto = await getMyBusiness();
      setBusiness(businessdto);
    }
    const businessId = localStorage.getItem("userBusinessId");
    if (businessId)
      fetchData(businessId);
  }, [isEditing, setBusiness]);

  return <>
    <Header/>
    <div className="businessPage">
      <h1>Your business</h1>
      <h3>Business info</h3>
      { isEditing ? <BusinessEditBox business={business} onClick={() => setEditing(false)}/> : 
      <BusinessInfoBox business={business} onClick={() => setEditing(true)}/>}
    </div>
  </>
}

export default BusinessPage;