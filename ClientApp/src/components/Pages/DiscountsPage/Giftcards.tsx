import { useState } from "react";
import { GetDiscountsDTO } from "../../../data/Responses";
import Pageination from "../../Pageination";
import DiscountCard from "./DiscountCard";
import CardComponent from "../../CardComponent";

function Giftcards() {
  const [page, setPage] = useState(1);
  const [giftcards, setGiftcards] = useState<GetDiscountsDTO | null>(null);

  const[trigger, setTrigger] = useState(0);

  return <>
  <CardComponent color="#dddddd">
    <h1>Giftcards</h1>
    <button type="button" onClick={()=> ""} className="btn btn-primary" disabled={false}>Add giftcard</button>
    {giftcards && giftcards.totalItems > 0
    ? giftcards.items.map((item) => <DiscountCard key={item.id} item={item} updatePage={() => setTrigger(trigger+1)}/>) 
    : <p>No giftcards available</p>}
    <Pageination selectedPage={page} totalPages={giftcards ? giftcards.totalPages : 0} setPage={(i) => console.log(i)}/>
  </CardComponent>
  </>
}

export default Giftcards;