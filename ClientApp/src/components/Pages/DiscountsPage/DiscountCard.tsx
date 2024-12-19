import { useState } from "react";
import { DiscountDTO, DiscountTarget, LoginResponseDTO, PricingStrategy } from "../../../data/Responses";
import CardComponent from "../../CardComponent";
import DialogBox from "../../DialogBox";
import { MissingAuthError } from "../../../data/MissingAuthError";
import { useNavigate } from "react-router";
import { deleteDiscountAPI } from "../../../data/APICalls";
import ScrollableList from "../../ScrollableList";

interface Param {
  item: DiscountDTO;
  updatePage: () => void;
}

function DiscountCard({item, updatePage}: Param) {
  const nav = useNavigate();

  const [isEdit, setEdit] = useState(false);
  const [isDelete, setDelete] = useState(false);

  const [code, setCode] = useState(item.code);
  const [amount, setAmount] = useState(`${item.amount}`);
  const [limit, setLimit] = useState(`${item.usageCountLimit}`);
  const [validFrom, setFrom] = useState(item.validFrom.slice(0, 10));
  const [validTo, setTo] = useState(item.validUntil.slice(0,10));

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  const onDelete = async () => {
    try {
      await deleteDiscountAPI(item.id, loginToken);
      setDelete(false);
      updatePage();
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else {
        throw err;
      }
    }
  }

  const onEdit = () => {
    try {
      setEdit(false);
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else {
        throw err;
      }
    }
  }

  return <>
    <DialogBox setVisibility={isDelete} question={`Are you sure you want to delete the discount?`} onAccept={onDelete} onCancel={() => setDelete(false)}></DialogBox>
    <CardComponent className="productCard fullWidth">
      {!isEdit ? <><div>
        <p>Code: {item.code}</p>
        <p>Amount: {item.amount} {item.valueType == PricingStrategy.FIXED_AMOUNT ? item.currency : "%"}</p>
        <p>Usage limit: {item.usageCountLimit}</p>
        <p>Already used: {item.usageCount}</p>
        <p>Valid from: {item.validFrom.slice(0,10)}</p>
        <p>Valid to: {item.validUntil.slice(0,10)}</p>
        <p>For: { item.target==DiscountTarget.All ? DiscountTarget.All : <ScrollableList>
            {item.entitledProductIds.map((item) => <p>Product id: {item}</p>)}
          </ScrollableList>}</p>
      </div>     
      <div className="buttonSlot">
        <button type="button" onClick={() => setEdit(true)} className="btn btn-primary">Edit</button>
        <button type="button" onClick={() => setDelete(true)} className="btn btn-danger">Delete</button>
      </div>
      </>:
      <div>
        <input value={code} onChange={(event) => {setCode(event.target.value)}} type="text" className="form-control" placeholder="Set code"/>
        <input value={amount} onChange={(event) => {setAmount(event.target.value)}} type="text" className="form-control" placeholder="Set amount"/>
        <p>Already used: {item.usageCount}</p>
        <input value={limit} onChange={(event) => {setLimit(event.target.value)}} type="text" className="form-control" placeholder="Set amount"/>
        <div className="dateForm">
          <label className="form-check-label" htmlFor="dateFrom">Set valid from:</label>
          <input value={validFrom} onChange={(event) => setFrom(event.target.value)} type="date" id="dateFrom"/>
        </div>
        <div className="dateForm">
          <label className="form-check-label" htmlFor="dateTo">Set valid until:</label>
          <input value={validTo} onChange={(event) => setTo(event.target.value)} type="date" id="dateTo"/>
        </div>
        <p>For: { item.target==DiscountTarget.All ? DiscountTarget.All : <ScrollableList>
            {item.entitledProductIds.map((item) => <p>Product id: {item}</p>)}
          </ScrollableList>}</p>
        <button type="button" onClick={onEdit} className="btn btn-success">Set</button>
        <button type="button" onClick={() =>setEdit(false)} className="btn btn-danger">Cancel</button>
      </div>}
    </CardComponent>
  </>
}

/*


    
  <Popup setVisibility={isEditModifiers}>
    <h3>Editing "{product.title}" modifiers</h3>
    <button type="button" onClick={onAddModifier} className="btn btn-primary">Add modifier</button>
    <button type="button" onClick={() =>setEditingModifiers(false)} className="btn btn-danger">Cancel</button>
    { product.compatibleModifiers.length > 0 && 
      <ScrollableList>
        {product.compatibleModifiers.map((item) => <ProductModifierCard key={item.id} prodModifier={item} updatePage={updatePage}/>)}
      </ScrollableList>
    } 
  </Popup>
  <Popup setVisibility={isAddModifier}>
    <input value={title} onChange={(event) => {setTitle(event.target.value)}} type="text" className="form-control" placeholder="Set title"/>
    <input value={price} onChange={(event) => {setPrice(event.target.value)}} type="text" className="form-control" placeholder="Set price"/>
    <input value={stock} onChange={(event) => {setStock(event.target.value)}} type="text" className="form-control" placeholder="Set stock count"/>
    <button type="button" onClick={onCreateModifier} className="btn btn-success">Create</button>
    <button type="button" onClick={onCancelAddModifier} className="btn btn-danger">Cancel</button>
  </Popup>

*/

export default DiscountCard;