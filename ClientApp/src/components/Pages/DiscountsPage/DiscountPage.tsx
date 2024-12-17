import Header from "../../Header";
import "./discount.css";
import Discounts from "./Discounts";
import Giftcards from "./Giftcards";

function DiscountPage() {


  return <>
    <Header/>
    <div className="discountPage">
      <div className="discounts">
        <Discounts/>
      </div>
      <div className="giftcards">
        <Giftcards/>
      </div>
    </div>
  </>
}

export default DiscountPage;