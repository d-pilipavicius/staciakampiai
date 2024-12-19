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
  const [orderId, setOrderId] = useState("");
  const [updateOrder, setUpdateOrder] = useState("");

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

  const updateOrderAPI = async () => {
    try {
      const response = await authAPI(address+`/v1/orders/${loginToken.user.businessId}/${orderId}`, "PUT", updateOrder, loginToken);
      
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
  };

  const order = async () => {
    const order = {
      "employeeId": loginToken.user.id,
      "businessId": loginToken.user.businessId,
      "items": items
    };
    try {
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
    <div style={{display: "flex", flexDirection: "row", alignItems: "center"}}>
    <CardComponent className="businessInfo">
      <h1>Create order</h1>
      <textarea value={items} onChange={(event) => {setItems(event.target.value)}} style={{ width: "50%", height: "200px"}}/>
      <button type="button" onClick={order}>Create</button>
    </CardComponent>
    <CardComponent className="businessInfo">
      <h1>Edit order</h1>
      <></>
      <input value={orderId} onChange={(event) => {setOrderId(event.target.value)}} type="text" className="form-control" placeholder="Set order id"/>
      <textarea value={updateOrder} onChange={(event) => {setUpdateOrder(event.target.value)}} style={{ width: "50%", height: "160px"}}/>
      <button type="button" onClick={updateOrderAPI}>Update</button>
    </CardComponent>
    </div>
    <ScrollableList>
      {"["}<br/>{products.map((item, index) => <>{"{"}"productId": "{item.id}","quantity": {item.quantityInStock}{"}"}{index+1 == products.length ? <></> : <>,<br/></> }</>)}<br/>{"]"}
    </ScrollableList>
  </>
}

export default OrderPage;