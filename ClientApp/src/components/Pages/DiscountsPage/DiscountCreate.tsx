import { useEffect, useState } from "react";
import Popup from "../../Popup";
import { Currency, DiscountTarget, GetProductsDTO, LoginResponseDTO, PricingStrategy } from "../../../data/Responses";
import { useNavigate } from "react-router";
import { getBusinessProductsAPI, postDiscountAPI } from "../../../data/APICalls";
import { MissingAuthError } from "../../../data/MissingAuthError";
import ProductDisplay from "./ProductDisplay";
import ScrollableList from "../../ScrollableList";
import Pageination from "../../Pageination";
import CardComponent from "../../CardComponent";

interface Param {
  isVisible: boolean;
  onCreate: () => void;
  onCancel: () => void;
}

const pageSize = 20;

function DiscountCreate({isVisible, onCreate, onCancel}: Param) {
  const nav = useNavigate();
  const [isProductsVisible, setProductsVisibility] = useState(false);
  const [products, setProducts] = useState<GetProductsDTO | null>(null);
  const [page, setPage] = useState(1);

  const [code, setCode] = useState("");
  const [amount, setAmount] = useState("");
  const [valueType, setValueType] = useState(PricingStrategy.FIXED_AMOUNT);
  const [validFrom, setValidFrom] = useState("");
  const [validUntil, setValidUntil] = useState("");
  const [target, setTarget] = useState(DiscountTarget.All);
  const [entitledProducts, setEntitledProducs] = useState<Set<string>>(new Set());
  const [usageCountLimit, setUsageCountLimit] = useState("");

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  useEffect(() => {
    getProducts();
  }, []);

  const getProducts = async () => {
    const loginString = localStorage.getItem("loginToken");
    if(!loginString) {
      nav("/");
      return;
    }
    const loginToken: LoginResponseDTO = JSON.parse(loginString); 
    try {
      const productsdto = await getBusinessProductsAPI(page-1, pageSize, loginToken.user.businessId, loginToken);
      setProducts(productsdto);
    } catch(err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }
  }  

  const createDiscount = async () => {
    if(!amount || !validFrom || !validUntil || !usageCountLimit)
      return;
    try {
      await postDiscountAPI({
        code: code,
        amount: Number(amount),
        valueType: valueType,
        currency: (valueType == PricingStrategy.FIXED_AMOUNT ? Currency.USD : null),
        validFrom: validFrom,
        validUntil: validUntil,
        target: target,
        entitledProductIds: (target == DiscountTarget.All ? [] : Array.from(entitledProducts)),
        businessId: loginToken.user.businessId,
        usageCountLimit: Number(usageCountLimit)
      }, loginToken);
      onCreate();
      onPressCreate();
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else
        throw err;
    }
  }

  const onPressCreate = () => {
    setCode("");
    setAmount("");
    setValueType(PricingStrategy.FIXED_AMOUNT);
    setValidFrom(""); setValidUntil("");
    setTarget(DiscountTarget.All);
    entitledProducts.clear();
    setUsageCountLimit("");
  } 

  return <>
  <Popup setVisibility={isProductsVisible} priority={1}>
    <ScrollableList>
      <div className="items">
        { products && products.items.length > 0
        ? products.items.map((item) =>     <CardComponent color="#eeeeee" className="product">
        <div className="productInside">
          <div>
            <p>Name: {item.title}</p>
            <p>Price: {item.price.amount} {item.price.currency}</p>
            <p>Left: {item.quantityInStock}</p>
          </div>
          <input className="form-check-input" type="checkbox" id="checkAll" checked={entitledProducts.has(item.id)} onClick={() => entitledProducts.has(item.id) ? entitledProducts.delete(item.id) : entitledProducts.add(item.id)}/>
        </div>
      </CardComponent>)
        : <p>No products found</p>}
      </div>
    </ScrollableList>
    <div className="horizontal">
      <button type="button" onClick={() => setProductsVisibility(false)} className="btn btn-primary">Close</button>
      <Pageination selectedPage={page} totalPages={products?.totalPages ?? 0} setPage={(id) => setPage(id)}/>
    </div>
  </Popup>
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
    <button type="button" onClick={onCancel} className="btn btn-danger">Cancel</button>
  </Popup>
  </>;
}

export default DiscountCreate;