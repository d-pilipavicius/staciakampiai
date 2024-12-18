import { useState } from "react";
import Popup from "../../Popup";
import ScrollableList from "../../ScrollableList";
import { GetProductsDTO, LoginResponseDTO } from "../../../data/Responses";
import { getBusinessProductsAPI } from "../../../data/APICalls";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";

interface Param {
  setVisibility: boolean;
}

const pageSize = 20;

function DiscountGetProducts({setVisibility}: Param) {
  const nav = useNavigate();
  
  const [page, setPage] = useState(1);
  const [products, setProducts] = useState<GetProductsDTO | null>(null);

  const getProducts = async () => {
    const loginString = localStorage.getItem("loginToken");
    if(!loginString) {
      nav("/");
      return;
    }
    const loginToken: LoginResponseDTO = JSON.parse(loginString); 
    try {
      const productsdto = await getBusinessProductsAPI(page, pageSize, loginToken.user.businessId, loginToken);
      setProducts(productsdto)
    } catch(err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }
  } 
  return <>
    <Popup setVisibility={setVisibility} priority={1}>
      <ScrollableList>
        {}
      </ScrollableList>
    </Popup>
  </>
}

export default DiscountGetProducts;