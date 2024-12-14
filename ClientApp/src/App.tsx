import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom";
import BusinessPage from "./components/Pages/BusinessPage/BusinessPage";
import OrderPage from "./components/Pages/OrderPage";
import ProductsPage from "./components/Pages/ProductsPage/ProductsPage";
import TaxPage from "./components/Pages/TaxPage";
import Home from "./components/Pages/Home";
import NotFound from "./components/Pages/NotFound";
import Login from "./components/Pages/Login/Login";
import { ReactNode, useEffect } from "react";

function App() {
  return <>
    <BrowserRouter>
      <AuthWrapper>
        <Routes>
          <Route index element={<Login/>}/>
          <Route path="/home" element={<Home/>}/>
          <Route path="/business" element={<BusinessPage/>}/>
          <Route path="/products" element={<ProductsPage/>}/>
          <Route path="/orders" element={<OrderPage/>}/>
          <Route path="/tax" element={<TaxPage/>}/>
          <Route path="/*" element={<NotFound/>}/>
        </Routes>
      </AuthWrapper>
    </BrowserRouter>
  </>
}

interface AuthWrapperProps {
  children: ReactNode;
}

function AuthWrapper({children}: AuthWrapperProps) {
  const nav = useNavigate();

  useEffect(() => {
    if(window.location.pathname != "/" && !localStorage.getItem("userId")){
      nav("/");
    }
  }, [nav]);

  return children;
}

export default App;