import { ReactNode, useEffect, useState } from "react";
import { BusinessDTO, GetProductsDTO } from "../../../data/Responses";
import Header from "../../Header";
import { getMyBusiness, getMyBusinessProducts, postProductAPI } from "../../../data/APICalls";
import Pageination from "../../Pageination";
import ProductCard from "./ProductCard";
import "./products.css"
import Popup from "../../Popup";
import ScrollableList from "../../ScrollableList";

const pageSize = 1;

function ProductsPage() {
  //Input boxes 
  const [trigger, setTrigger] = useState(0);
  const [title, setTitle] = useState("");
  const [quantityInStock, setQuantityInStock] = useState("");
  const [price, setPrice] = useState("");
  const [compatibleModifierIds, setCompatibleModifierIds] = useState([]);

  const [isVisible, setVisibility] = useState(false); 
  const [products, setProducts] = useState<GetProductsDTO | null>();
  const [page, setPage] = useState(1);

  const businessId = localStorage.getItem("userBusinessId");

  const getProducts = async () => {
    const productsdto = await getMyBusinessProducts(page, pageSize);
    if(productsdto) setProducts(productsdto);
  };

  useEffect(() => {
    getProducts();
  }, [trigger]);

  const onSetPage = (id: number) => {
    setPage(id);
    setTrigger(trigger+1);
  };

  const createProduct = async () => {
    if(businessId) {
      const response = await postProductAPI({
        title: title,
        quantityInStock: Number(quantityInStock),
        businessId: businessId,
        price: {
          amount: Number(price),
          currency: "USD",
        },
        compatibleModifiers: []
      })
      if(response) getProducts();
    }
    setTitle("");
    setQuantityInStock("");
    setPrice("");
    setCompatibleModifierIds([]);
    setVisibility(false);
  }

  return <>
    <Header/>
    <Popup setVisibility={isVisible}>
      <h1>Add new product</h1>
      <input value={title} onChange={(event) => {setTitle(event.target.value)}} type="text" className="form-control" placeholder="Set title"/>
      <input value={quantityInStock} onChange={(event) => {setQuantityInStock(event.target.value)}} type="text" className="form-control" placeholder="Set quantity"/>
      <input value={price} onChange={(event) => {setPrice(event.target.value)}} type="text" className="form-control" placeholder="Set price"/>
      <button type="button" onClick={createProduct} className="btn btn-success" disabled={!Boolean(businessId)}>Submit</button>
      <button type="button" onClick={() => setVisibility(false)} className="btn btn-danger" disabled={!Boolean(businessId)}>Cancel</button>
    </Popup>
    <div className="productPage">
      <h1>Business products</h1>
      <button type="button" onClick={()=>setVisibility(true)} className="btn btn-primary" disabled={!Boolean(businessId)}>Add product</button>
      {products && products.totalItems > 0
      ? products.items.map((item) => <ProductCard key={item.id} product={item} updatePage={() => setTrigger(trigger+1)}/>) 
      : <p>No products available</p>}
      <Pageination selectedPage={page} totalPages={products ? products.totalPages : 0} setPage={onSetPage}/>
    </div>
  </>
}

export default ProductsPage;