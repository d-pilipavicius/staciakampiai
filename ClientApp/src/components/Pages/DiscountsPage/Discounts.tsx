import { useEffect, useState } from "react";
import { Currency, DiscountTarget, GetDiscountsDTO, GetProductsDTO, LoginResponseDTO, PricingStrategy } from "../../../data/Responses";
import DiscountCard from "./DiscountCard";
import Pageination from "../../Pageination";
import CardComponent from "../../CardComponent";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";
import { getDiscountsAPI, postDiscountAPI } from "../../../data/APICalls";
import DiscountCreate from "./DiscountCreate";

const pageSize = 20;

function Discounts() {
  const nav = useNavigate();

  const [page, setPage] = useState(1);
  const [discounts, setDiscounts] = useState<GetDiscountsDTO | null>(null);
  
  const[trigger, setTrigger] = useState(0);
  const[isCreating, setCreating] = useState(false);

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  const getDiscounts = async () => {
    try {
      const discounts = await getDiscountsAPI(page-1, pageSize, loginToken.user.businessId, loginToken);
      setDiscounts(discounts);
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else
        throw err;
    }
  }

  useEffect(() => {
    getDiscounts();
  }, [trigger]);

  const onCreate = () => {
    setTrigger(trigger+1);
    setCreating(false);
  }

    //As a field add: usageCount    
  return <>
    <DiscountCreate isVisible={isCreating} onCreate={onCreate} onCancel={() => setCreating(false)}/>
    <CardComponent color="#dddddd" className="fullWidth">
      <h1>Discounts</h1>
      <button type="button" onClick={()=> setCreating(true)} className="btn btn-primary">Add discount</button>
      {discounts && discounts.totalItems > 0
      ? discounts.items.map((item) => <DiscountCard key={item.id} item={item} updatePage={() => setTrigger(trigger+1)}/>) 
      : <p>No discounts available</p>}
      <Pageination selectedPage={page} totalPages={discounts ? discounts.totalPages : 0} setPage={(i) => setPage(i)}/>
    </CardComponent>      
  </>
}

export default Discounts;