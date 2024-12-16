import { useEffect, useState } from "react";
import { Currency, DiscountTarget, GetDiscountsDTO, GetProductsDTO, PricingStrategy } from "../../../data/Responses";
import DiscountCard from "./DiscountCard";
import Pageination from "../../Pageination";
import CardComponent from "../../CardComponent";
import Popup from "../../Popup";

const pageSize = 20;

function Discounts() {
  const [page, setPage] = useState(1);
  const [discounts, setDiscounts] = useState<GetDiscountsDTO | null>(null);

  
  const[trigger, setTrigger] = useState(0);
  const[isVisible, setVisibility] = useState(false);
  const[isProductsVisible, setProductsVisibility] = useState(false);

  //As a field add: usageCount      
  const[code, setCode] = useState("");
  const[amount, setAmount] = useState("");
  const[valueType, setValueType] = useState(PricingStrategy.FIXED_AMOUNT);
  const[validFrom, setValidFrom] = useState("");
  const[validUntil, setValidUntil] = useState("");
  const[target, setTarget] = useState(DiscountTarget.All);
  const entitledProducts = [];
  const[usageCountLimit, setUsageCountLimit] = useState("");

  const getDiscounts = async () => {
    //Add API implementation
    //REMOVE AFTER ADDED
    setDiscounts({
      "totalItems": 1,
      "totalPages": 1,
      "currentPage": 0,
      "items": [{
        id: "",
        code: "ABCD!",
        amount: 12345,
        usageCount: 1,
        valueType: PricingStrategy.FIXED_AMOUNT,
        currency: Currency.USD,
        validFrom: "",
        validUntil: "",
        target: DiscountTarget.All,
        entitledProductsIds: [],
        businessId: "",
        usageCountLimit: 3
      }]
    });
  }

  useEffect(() => {
    getDiscounts();
  }, [trigger]);

  const createDiscount = async () => {
    //Add API
    setVisibility(false);
  }

  const onPressCreate = () => {
    setCode("");
    setAmount("");
    setValueType(PricingStrategy.FIXED_AMOUNT);
    setValidFrom(""); setValidUntil("");
    setTarget(DiscountTarget.All);
    entitledProducts.length = 0;
    setUsageCountLimit("");
  } 

  /* 
  const entitledProducts = [];
  */
  
  return <>
    <Popup setVisibility={isVisible}>
      <h1>Add new discount</h1>
      <form>
        <input value={code} onChange={(event) => {setCode(event.target.value)}} type="text" className="form-control" placeholder="Set discount code"/>
        <input value={amount} onChange={(event) => {setAmount(event.target.value)}} type="text" className="form-control" placeholder="Set discount code value"/>
        <input value={usageCountLimit} onChange={(event) => {setUsageCountLimit(event.target.value)}} type="text" className="form-control" placeholder="Set discount usage limit"/>
        <h4>Discount type (flat cash or %)</h4>
        <div className="checkBoxes">
          <div className="form-check">
            <input className="form-check-input" type="radio" name="fixedRadio" id="fixedRadio" checked={valueType == PricingStrategy.FIXED_AMOUNT} onClick={() => setValueType(PricingStrategy.FIXED_AMOUNT)}/>
            <label className="form-check-label" htmlFor="fixedRadio">Fixed</label>
          </div>
          <div className="form-check">
            <input className="form-check-input" type="radio" name="percRadio" id="percRadio" checked={valueType == PricingStrategy.PERCENTAGE} onClick={() => setValueType(PricingStrategy.PERCENTAGE)}/>
            <label className="form-check-label" htmlFor="percRadio">Percentage</label>
          </div>
        </div>
        <div className="dateForm">
          <label className="form-check-label" htmlFor="dateFrom">Set valid from:</label>
          <input value={validFrom} onChange={(event) => setValidFrom(event.target.value)} type="date" id="dateFrom"/>
        </div>
        <div className="dateForm">
          <label className="form-check-label" htmlFor="dateTo">Set valid until:</label>
          <input value={validUntil} onChange={(event) => setValidUntil(event.target.value)} type="date" id="dateTo"/>
        </div>
        <div className="form-check">
          <label className="form-check-label" htmlFor="checkAll">Discount all products</label>
          <input className="form-check-input" type="checkbox" id="checkAll" checked={target==DiscountTarget.All} onClick={() => setTarget(target == DiscountTarget.All ? DiscountTarget.Entitled : DiscountTarget.All)}/>
          <button type="button" onClick={() => setProductsVisibility(true)} className="btn btn-primary" disabled={target==DiscountTarget.All}>Add Products</button>
        </div>
      </form>
      <button type="button" onClick={createDiscount} className="btn btn-success">Submit</button>
      <button type="button" onClick={() => setVisibility(false)} className="btn btn-danger">Cancel</button>
    </Popup>
    <CardComponent color="#dddddd">
      <h1>Discounts</h1>
      <button type="button" onClick={()=> setVisibility(true)} className="btn btn-primary" disabled={!Boolean(discounts)}>Add discount</button>
      {discounts && discounts.totalItems > 0
      ? discounts.items.map((item) => <DiscountCard key={item.id} item={item} updatePage={() => setTrigger(trigger+1)}/>) 
      : <p>No discounts available</p>}
      <Pageination selectedPage={page} totalPages={discounts ? discounts.totalPages : 0} setPage={(i) => console.log(i)}/>
    </CardComponent>      
  </>
}

export default Discounts;