import { useState } from "react";
import { LoginResponseDTO, ProductDTO, ProductModifierDTO } from "../../../data/Responses";
import CardComponent from "../../CardComponent";
import DialogBox from "../../DialogBox";
import { deleteProductModifierAPI, putProductModifierAPI } from "../../../data/APICalls";
import "./products.css"
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";

interface Props {
  prodModifier: ProductModifierDTO;
  updatePage: () => void;
}

function ProductModifierCard ({prodModifier, updatePage}: Props) {
  const nav = useNavigate();

  const [isDelete, setDelete] = useState(false);
  const [isEdit, setEditing] = useState(false);

  const [title, setTitle] = useState(prodModifier.title);
  const [price, setPrice] = useState(String(prodModifier.price.amount));
  const [stock, setStock] = useState(String(prodModifier.quantityInStock));

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  const onDelete = async () => {
    try {
      await deleteProductModifierAPI(prodModifier.id, loginToken);
      setDelete(false);
      updatePage();
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }
  };

  const onEdit = async () => {
    try {
      const data = await putProductModifierAPI(prodModifier.id, {
        "title": title,
        "price": {
          "amount": Number(price),
          "currency": prodModifier.price.currency
        },
        "quantityInStock": Number(stock),
      }, loginToken);
      prodModifier.title = data.title;
      prodModifier.price = data.price;
      prodModifier.quantityInStock = data.quantityInStock;
      setEditing(false);
      updatePage();
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }
  }

  return <>
  <DialogBox setVisibility={isDelete} question={`Are you sure you want to delete "${prodModifier.title}" from the product modifier list?`} onAccept={onDelete} onCancel={() => setDelete(false)}/>
  <CardComponent color="#eeeeee" className="productModifierCard">
    {!isEdit ? <><div>
      <p>Title: {prodModifier.title}</p>
      <p>Price: {prodModifier.price.amount} {prodModifier.price.currency}</p>
      <p>In stock: {prodModifier.quantityInStock}</p>
    </div>     
    <div className="buttonSlot">
      <button type="button" onClick={() => setEditing(true)} className="btn btn-primary">Edit</button>
      <button type="button" onClick={() => setDelete(true)} className="btn btn-danger">Delete</button>
    </div>
    </>:
    <div>
      <input value={title} onChange={(event) => {setTitle(event.target.value)}} type="text" className="form-control" placeholder="Set title"/>
      <input value={price} onChange={(event) => {setPrice(event.target.value)}} type="text" className="form-control" placeholder="Set price"/>  
      <input value={stock} onChange={(event) => {setStock(event.target.value)}} type="text" className="form-control" placeholder="Set stock count"/>
      <button type="button" onClick={onEdit} className="btn btn-success">Set</button>
      <button type="button" onClick={() =>setEditing(false)} className="btn btn-danger">Cancel</button>
    </div>}
  </CardComponent>
  </>
}

export default ProductModifierCard;