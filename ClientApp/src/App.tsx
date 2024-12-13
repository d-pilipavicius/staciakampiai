import { BrowserRouter, Route, Routes } from "react-router-dom";
import BusinessPage from "./components/Pages/BusinessPage/BusinessPage";
import OrderPage from "./components/Pages/OrderPage";
import ProductsPage from "./components/Pages/ProductsPage/ProductsPage";
import TaxPage from "./components/Pages/TaxPage";
import Home from "./components/Pages/Home";
import NotFound from "./components/Pages/NotFound";
import Login from "./components/Pages/Login/Login";
import Popup from "./components/Popup";

function App() {
  return <>
    <BrowserRouter>
      <Routes>
        <Route index element={<Login/>}/>
        <Route path="/home" element={<Home/>}/>
        <Route path="/business" element={<BusinessPage/>}/>
        <Route path="/products" element={<ProductsPage/>}/>
        <Route path="/orders" element={<OrderPage/>}/>
        <Route path="/tax" element={<TaxPage/>}/>
        <Route path="/*" element={<NotFound/>}/>
      </Routes>
    </BrowserRouter>
  </>
}

export default App;