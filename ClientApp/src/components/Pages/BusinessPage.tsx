import { useEffect, useState } from "react";
import Header from "../Header";
import { getBusinessAPI, getUserAPI } from "../../data/APICalls";
import { BusinessDTO, UserDTO } from "../../data/Responses";
import BusinessInfoBox from "../BusinessInfoBox";
import BusinessEditBox from "../BusinessEditBox";

function BusinessPage() {
  const [isEditing, setEditing] = useState(false);
  const [business, setBusiness] = useState<BusinessDTO | null>(null);

  useEffect(() => {
    if(isEditing)
      return;
    const fetchData = async (id: string) => {
      const userdto: UserDTO = await getUserAPI(id);
      const businessdto = await getBusinessAPI(userdto.businessId);
      setBusiness(businessdto);
    }
    const userId = localStorage.getItem("userId");
    if (userId)
      fetchData(userId);
  }, []);

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