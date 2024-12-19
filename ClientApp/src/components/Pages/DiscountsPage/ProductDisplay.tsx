import { useState } from "react";
import { ProductDTO } from "../../../data/Responses";
import CardComponent from "../../CardComponent";

interface Param {
  product: ProductDTO;
  list: Set<string>;
  updateList: (set: Set<string>) => void;
}

function ProductDisplay({product, list, updateList}: Param) {
  const [isSelected, setSelected] = useState(list.has(product.id));
  
  const onClick = () => {
    isSelected ? list.add(product.id) : list.delete(product.id);
    updateList(list);
    setSelected(!isSelected);
  } 
  
  return <>
    <CardComponent color="#eeeeee" className="product">
      <div className="productInside">
        <div>
          <p>Name: {product.title}</p>
          <p>Price: {product.price.amount} {product.price.currency}</p>
          <p>Left: {product.quantityInStock}</p>
        </div>
        <input className="form-check-input" type="checkbox" id="checkAll" checked={isSelected} onClick={onClick}/>
      </div>
    </CardComponent>
  </>
}

export default ProductDisplay;