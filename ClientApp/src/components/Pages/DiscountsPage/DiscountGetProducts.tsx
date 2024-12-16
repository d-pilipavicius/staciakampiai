import { useState } from "react";
import Popup from "../../Popup";
import ScrollableList from "../../ScrollableList";
import { GetProductsDTO } from "../../../data/Responses";
import { getMyBusinessProducts } from "../../../data/APICalls";

interface Param {
  setVisibility: boolean;
}

const pageSize = 20;

function DiscountGetProducts({setVisibility}: Param) {
  const [page, setPage] = useState(1);
  const [products, setProducts] = useState<GetProductsDTO | null>(null);

  const getProducts = async () => {
    const productsdto = await getMyBusinessProducts(page, pageSize);
    if(productsdto) setProducts(productsdto);
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