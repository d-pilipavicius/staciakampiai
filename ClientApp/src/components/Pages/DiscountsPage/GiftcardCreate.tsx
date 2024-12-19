import { useEffect, useState } from "react";
import Popup from "../../Popup";
import { Currency, DiscountTarget, GetProductsDTO, LoginResponseDTO, PricingStrategy, ProductDTO } from "../../../data/Responses";
import { useNavigate } from "react-router";
import { getBusinessProductsAPI, postDiscountAPI } from "../../../data/APICalls";
import { MissingAuthError } from "../../../data/MissingAuthError";

interface Param {
  isVisible: boolean;
  onCreate: () => void;
  onCancel: () => void;
}

const pageSize = 20;

function GiftcardCreate({isVisible, onCreate, onCancel}: Param) {
  const nav = useNavigate();
  const [page, setPage] = useState(1);

  const [code, setCode] = useState("");
  const [amount, setAmount] = useState("");
  const [valueType, setValueType] = useState(PricingStrategy.FIXED_AMOUNT);
  const [validFrom, setValidFrom] = useState("");
  const [validUntil, setValidUntil] = useState("");
  const [target, setTarget] = useState(DiscountTarget.All);

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  const createDiscount = async () => {
    if(!amount || !validFrom || !validUntil)
      return;
    try {
      const a = {
        code: code,
        amount: Number(amount),
        valueType: valueType,
        currency: (valueType == PricingStrategy.FIXED_AMOUNT ? Currency.USD : null),
        validFrom: validFrom,
        validUntil: validUntil,
        target: DiscountTarget.All,
        entitledProductIds: [],
        businessId: loginToken.user.businessId,
        usageCountLimit: 1
      };
      await postDiscountAPI(a, loginToken);

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
  } 

  return <>
  <Popup setVisibility={isVisible}>
    <h1>Add new giftcard</h1>
    <form>
      <input value={code} onChange={(event) => {setCode(event.target.value)}} type="text" className="form-control" placeholder="Set giftcard code"/>
      <input value={amount} onChange={(event) => {setAmount(event.target.value)}} type="text" className="form-control" placeholder="Set discount value"/>
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
    </form>
    <button type="button" onClick={createDiscount} className="btn btn-success">Submit</button>
    <button type="button" onClick={onCancel} className="btn btn-danger">Cancel</button>
  </Popup>
  </>;
}

export default GiftcardCreate;