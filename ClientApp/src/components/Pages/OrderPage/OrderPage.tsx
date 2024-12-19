import { useNavigate } from "react-router";
import CardComponent from "../../CardComponent";
import Header from "../../Header";
import { LoginResponseDTO, ProductDTO } from "../../../data/Responses";
import { useEffect, useState } from "react";
import { authAPI, getBusinessProductsAPI } from "../../../data/APICalls";
import { address } from "../../../data/Routes";
import { MissingAuthError } from "../../../data/MissingAuthError";
import ScrollableList from "../../ScrollableList";

function OrderPage() {
  const nav = useNavigate();

  const [items, setItems] = useState("");
  const [products, setProducts] = useState<ProductDTO[]>([]);

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  useEffect(() => {
    getProducts();
  },[])

  const getProducts = async () => {
    try {
      const productsdto = await getBusinessProductsAPI(0, 1000, loginToken.user.businessId, loginToken);
      setProducts(productsdto.items);
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }
  };


  const order = async () => {
    const order = {
      "employeeId": loginToken.user.id,
      "businessId": loginToken.user.businessId,
      "items": items
    };
    try {
      console.log(`{"employeeId": "${order.employeeId}", "businessId": "${order.businessId}", "items": ${items}}`);
      const response = await authAPI(address+`/v1/orders/${loginToken.user.businessId}`, "POST", `{"employeeId": "${order.employeeId}", "businessId": "${order.businessId}", "items": ${items}}`, loginToken);
      
      if (!response.ok) {
        throw new Error(`Error ${response.status}: ${response.text}`);
      }
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }

  }

  return <>
    <Header/>
    <CardComponent className="businessInfo">
      <h1>Create order</h1>
      <textarea value={items} onChange={(event) => {setItems(event.target.value)}} style={{width: "50%"}}/>
      <button type="button" onClick={order}>Create</button>
    </CardComponent>
    <ScrollableList>
      {"["}<br/>{products.map((item, index) => <>{"{"}"productId": "{item.id}","quantity": {item.quantityInStock}{"}"}{index+1 == products.length ? <></> : <>,<br/></> }</>)}<br/>{"]"}
    </ScrollableList>
  </>
}

export default OrderPage;