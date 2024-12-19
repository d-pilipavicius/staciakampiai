import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom";
import BusinessPage from "./components/Pages/BusinessPage/BusinessPage";
import OrderPage from "./components/Pages/OrderPage/OrderPage";
import ProductsPage from "./components/Pages/ProductsPage/ProductsPage";
import Home from "./components/Pages/Home";
import NotFound from "./components/Pages/NotFound";
import Login from "./components/Pages/Login/Login";
import { ReactNode, useEffect } from "react";
import TaxPage from "./components/Pages/TaxPage/TaxPage";
import DiscountPage from "./components/Pages/DiscountsPage/DiscountPage";
import ServiceChargePage from "./components/Pages/ServiceChargesPage/ServiceChargePage";
import PaymentPage from "./components/Pages/PaymentsPage/PaymentPage";
import ReservationsPage from "./components/Pages/ReservationsPage/ReservationsPage";

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
          <Route path="/discounts" element={<DiscountPage/>}/>
          <Route path="/service-charges" element={<ServiceChargePage/>}/>
          <Route path="/payments" element={<PaymentPage/>}/>
          <Route path="/reservations" element={<ReservationsPage/>}/>
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
    if(window.location.pathname != "/" && !localStorage.getItem("loginToken")){
      nav("/");
    }
  }, [nav]);

  return children;
}

export default App;