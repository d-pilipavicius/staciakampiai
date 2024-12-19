import { useEffect, useState } from "react";
import { GetDiscountsDTO, LoginResponseDTO } from "../../../data/Responses";
import Pageination from "../../Pageination";
import CardComponent from "../../CardComponent";
import { useNavigate } from "react-router";
import { getGiftcardsAPI } from "../../../data/APICalls";
import { MissingAuthError } from "../../../data/MissingAuthError";
import GiftcardCreate from "./GiftcardCreate";
import GiftcardCard from "./GiftcardCard";

const pageSize = 20;

function Giftcards() {
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
      const discounts = await getGiftcardsAPI(page-1, pageSize, loginToken.user.businessId, loginToken);
      console.log(JSON.stringify(discounts));
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
  
  return <>
    <GiftcardCreate isVisible={isCreating} onCreate={onCreate} onCancel={() => setCreating(false)}/>
    <CardComponent color="#dddddd" className="fullWidth">
      <h1>Giftcards</h1>
      <button type="button" onClick={()=> setCreating(true)} className="btn btn-primary">Add giftcard</button>
      {discounts && discounts.totalItems > 0
      ? discounts.items.map((item) => <GiftcardCard key={item.id} item={item} updatePage={() => setTrigger(trigger+1)}/>) 
      : <p>No giftcards available</p>}
      <Pageination selectedPage={page} totalPages={discounts ? discounts.totalPages : 0} setPage={(i) => setPage(i)}/>
    </CardComponent>      
  </>
}

export default Giftcards;