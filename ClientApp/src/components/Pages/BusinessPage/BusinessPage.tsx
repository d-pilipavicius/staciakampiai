import { useEffect, useState } from "react";
import Header from "../../Header";
import { getBusinessAPI, getUsersByBusinessAPI } from "../../../data/APICalls";
import { BusinessDTO, LoginResponseDTO, UserDTO } from "../../../data/Responses";
import BusinessInfoBox from "./BusinessInfoBox";
import BusinessEditBox from "./BusinessEditBox";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";
import ScrollableList from "../../ScrollableList";

function BusinessPage() {
  const nav = useNavigate();

  const [isEditing, setEditing] = useState(false);
  const [business, setBusiness] = useState<BusinessDTO | null>(null);
  const [users, setUsers] = useState<UserDTO[]>([]);

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  useEffect(() => {
    if(isEditing)
      return;
    const fetchData = async () => {
      try {
        const businessdto = await getBusinessAPI(loginToken.user.businessId, loginToken);
        setBusiness(businessdto);
      } catch (err) {
        if(err instanceof MissingAuthError) {
          nav("/");
          return;
        } else 
          throw err;
      }
    }
    fetchData();
    getUsers();
  }, [isEditing, setBusiness]);

  const getUsers = async () => {
    try {
      const users = await getUsersByBusinessAPI(loginToken.user.businessId, loginToken);
      setUsers(users);
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        throw err;
      } else 
        throw err;
    }
  }

  return <>
    <Header/>
    <div className="businessPage">
      <h1>Your business</h1>
      <h3>Business info</h3>
      { isEditing ? <BusinessEditBox business={business} onClick={() => setEditing(false)}/> : 
      <BusinessInfoBox business={business} onClick={() => setEditing(true)}/>}
      {loginToken.user.role == "BusinessOwner" 
      ? <ScrollableList>
        {users.map((item) => <p>User: {item.fullName}</p>)}
      </ScrollableList>
      : ""}
    </div>
  </>
}

export default BusinessPage;