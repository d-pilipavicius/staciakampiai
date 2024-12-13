import { useState } from "react";
import { ProductDTO } from "../../../data/Responses";
import CardComponent from "../../CardComponent";
import DialogBox from "../../DialogBox";
import { deleteProductAPI, putProductAPI } from "../../../data/APICalls";

interface Props {
  product: ProductDTO;
  updatePage: () => void;
}

function ProductCard({product, updatePage}: Props) {
  
  const [isDelete, setDelete] = useState(false);
  const [isEdit, setEditing] = useState(false);
  
  const [title, setTitle] = useState(product.title);
  const [price, setPrice] = useState(String(product.price.amount));
  const [stock, setStock] = useState(String(product.quantityInStock));

  const onDelete = async () => {
    await deleteProductAPI(product.id);
    setDelete(false);
    updatePage();
  }

  const onEdit = async () => {
    const data = await putProductAPI(product.id, {
      "title": title,
      "price": {
        "amount": Number(price),
        "currency": product.price.currency
      },
      "quantityInStock": Number(stock),
      "compatibleModifiers": product.compatibleModifiers,
    });
    product.title = data.title;
    product.price = data.price;
    product.quantityInStock = data.quantityInStock;
    setEditing(false);
    updatePage();
  }

  return <CardComponent className="productCard">
    {!isEdit ? <><div>
      <p>Title: {product.title}</p>
      <p>Price: {product.price.amount} {product.price.currency}</p>
      <p>In stock: {product.quantityInStock}</p>
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

    <DialogBox setVisibility={isDelete} question={`Are you sure you want to delete "${product.title}" from the product list?`} onAccept={onDelete} onCancel={() => setDelete(false)}></DialogBox>
    </CardComponent>
}

export default ProductCard;