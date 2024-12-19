import { useEffect, useState } from "react";
import { Currency, GetProductsDTO, LoginResponseDTO } from "../../../data/Responses";
import Header from "../../Header";
import { getBusinessProductsAPI, postProductAPI } from "../../../data/APICalls";
import Pageination from "../../Pageination";
import ProductCard from "./ProductCard";
import "./products.css"
import Popup from "../../Popup";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";

const pageSize = 20;

function ProductsPage() {
  const nav = useNavigate();
  //Input boxes 
  const [trigger, setTrigger] = useState(0);
  const [title, setTitle] = useState("");
  const [quantityInStock, setQuantityInStock] = useState("");
  const [price, setPrice] = useState("");

  const [isVisible, setVisibility] = useState(false); 
  const [products, setProducts] = useState<GetProductsDTO | null>();
  const [page, setPage] = useState(1);

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  const getProducts = async () => {
    try {
      const productsdto = await getBusinessProductsAPI(page-1, pageSize, loginToken.user.businessId, loginToken);
      setProducts(productsdto);
    } catch (err) {
      if(err instanceof MissingAuthError) {
        nav("/");
        return;
      } else 
        throw err;
    }
  };

  useEffect(() => {
    getProducts();
  }, [trigger]);

  const onSetPage = (id: number) => {
    setPage(id);
    setTrigger(trigger+1);
  };

  const createProduct = async () => {
    try {
      await postProductAPI({
        "title": title,
        "quantityInStock": Number(quantityInStock),
        "businessId": loginToken.user.businessId,
        "price": {
          "amount": Number(price),
          "currency": Currency.USD,
        },
        "compatibleModifierIds": []
      }, loginToken);
      setTitle("");
      setQuantityInStock("");
      setPrice("");
      getProducts();
      setVisibility(false);
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
    <Popup setVisibility={isVisible}>
      <h1>Add new product</h1>
      <input value={title} onChange={(event) => {setTitle(event.target.value)}} type="text" className="form-control" placeholder="Set title"/>
      <input value={quantityInStock} onChange={(event) => {setQuantityInStock(event.target.value)}} type="text" className="form-control" placeholder="Set quantity"/>
      <input value={price} onChange={(event) => {setPrice(event.target.value)}} type="text" className="form-control" placeholder="Set price"/>
      <button type="button" onClick={createProduct} className="btn btn-success">Submit</button>
      <button type="button" onClick={() => setVisibility(false)} className="btn btn-danger">Cancel</button>
    </Popup>
    <div className="productPage">
      <h1>Business products</h1>
      <button type="button" onClick={()=>setVisibility(true)} className="btn btn-primary">Add product</button>
      {products && products.totalItems > 0
      ? products.items.map((item) => <ProductCard key={item.id} product={item} updatePage={() => setTrigger(trigger+1)}/>) 
      : <p>No products available</p>}
      <Pageination selectedPage={page} totalPages={products ? products.totalPages : 0} setPage={onSetPage}/>
    </div>
  </>
}

export default ProductsPage;