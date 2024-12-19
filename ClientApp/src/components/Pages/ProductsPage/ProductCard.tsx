import { useState } from "react";
import { Currency, LoginResponseDTO, ProductDTO, ProductModifierDTO, PutProductDTO } from "../../../data/Responses";
import CardComponent from "../../CardComponent";
import DialogBox from "../../DialogBox";
import { deleteProductAPI, deleteProductModifierAPI, getProductModifierAPI, postProductModifierAPI, putProductAPI } from "../../../data/APICalls";
import Popup from "../../Popup";
import ScrollableList from "../../ScrollableList";
import ProductModifierCard from "./ProductModifierCard";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";

interface Props {
  product: ProductDTO;
  updatePage: () => void;
}

function ProductCard({product, updatePage}: Props) {
  const nav = useNavigate();

  const [isDelete, setDelete] = useState(false);
  const [isEdit, setEditing] = useState(false);
  const [isEditModifiers, setEditingModifiers] = useState(false);
  const [isAddModifier, setAddModifier] = useState(false);
  
  const [title, setTitle] = useState(product.title);
  const [price, setPrice] = useState(String(product.price.amount));
  const [stock, setStock] = useState(String(product.quantityInStock));

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  const onDelete = async () => {
    try {
      await deleteProductAPI(product.id, loginToken);
      product.compatibleModifiers.map(async (item) => deleteProductModifierAPI(item.id, loginToken));
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }
    setDelete(false);
    updatePage();
  }

  const onEdit = async () => {
    try {
      const data = await putProductAPI(product.id, {
        "title": title,
        "price": {
          "amount": Number(price),
          "currency": product.price.currency
        },
        "quantityInStock": Number(stock),
        "compatibleModifierIds": product.compatibleModifiers.map((item) => item.id),
      }, loginToken);
      product.title = data.title;
      product.price = data.price;
      product.quantityInStock = data.quantityInStock;
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

  const onCreateModifier = async () => {
    try {
      const modifier = await postProductModifierAPI({
        "title": title,
        "price": {
          "amount": Number(price),
          "currency": Currency.USD
        },
        "quantityInStock": Number(stock),
        "businessId": product.businessId
      }, loginToken);
      const modifList = product.compatibleModifiers.map((item) => item.id);
      modifList.push(modifier.id);
      const putProduct: PutProductDTO = {
        "price": product.price,
        "title": product.title,
        "quantityInStock": product.quantityInStock,
        "compatibleModifierIds": modifList
      };
      await putProductAPI(product.id, putProduct, loginToken);
      onCancelAddModifier();
      updatePage();
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }
  }

  const onAddModifier = () => {
    setTitle("");
    setPrice("");
    setStock("");
    setEditingModifiers(false);
    setAddModifier(true);
  }

  const onCancelAddModifier = () => {
    setEditingModifiers(true);
    setAddModifier(false);
  }

  return <>
  <DialogBox setVisibility={isDelete} question={`Are you sure you want to delete "${product.title}" from the product list?`} onAccept={onDelete} onCancel={() => setDelete(false)}></DialogBox>
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
  <CardComponent className="productCard">
    {!isEdit ? <><div>
      <p>Title: {product.title}</p>
      <p>Price: {product.price.amount} {product.price.currency}</p>
      <p>In stock: {product.quantityInStock}</p>
      <p>Modifier count: {product.compatibleModifiers.length}</p>
    </div>     
    <div className="buttonSlot">
      <button type="button" onClick={() => setEditing(true)} className="btn btn-primary">Edit</button>
      <button type="button" onClick={() => setEditingModifiers(true)} className="btn btn-primary">Edit Modifiers</button>
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

export default ProductCard;