import React, { useState, useEffect } from "react";
import {
  postTipAPI,
  getTipsAPI,
  postCardPaymentAPI,
  postCashPaymentAPI,
  postInitiateRefundAPI
} from "../../../data/APICalls";
import { AddTipDTO, TipDTO, LoginResponseDTO, GetTipDTO, CreatePaymentDTO, PaymentMethod, OrderItemPaymentDTO, Currency } from "../../../data/Responses";
import TipForm from "./TipForm";
import Header from "../../Header";
import "./paymentPage.css";
import Popup from "../../Popup";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";

const PaymentPage: React.FC = () => {
  const nav = useNavigate();

  const [tips, setTips] = useState<TipDTO[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const pageSize = 10;
  const [isFormVisible, setIsFormVisible] = useState(false);

  const [paymentMethod, setPaymentMethod] = useState<PaymentMethod>(PaymentMethod.CARD);
  const [orderId, setOrderId] = useState("");
  const [businessId, setBusinessId] = useState("");
  const [employeeId, setEmployeeId] = useState("770e8400-e29b-41d4-a716-446655440222");
  const [currency, setCurrency] = useState<Currency>(Currency.USD);
  const [orderItems, setOrderItems] = useState<OrderItemPaymentDTO[]>([]);

  const [paymentResponse, setPaymentResponse] = useState<any>(null);
  const [recentPayments, setRecentPayments] = useState<any[]>([]);

  const loginString = localStorage.getItem("loginToken");
  if (!loginString) {
    nav("/");
    return null;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  useEffect(() => {
    loadTips();
    // Load recent payments from localStorage when the component mounts
    const savedPayments = localStorage.getItem("recentPayments");
    if (savedPayments) {
      setRecentPayments(JSON.parse(savedPayments));
    }
  }, [currentPage]);

  const loadTips = async () => {
    try {
      const data: GetTipDTO = await getTipsAPI(currentPage, pageSize, loginToken);
      setTips(data.items);
      setTotalPages(data.totalPages);
    } catch (error) {
      if (error instanceof MissingAuthError) {
        nav("/");
        return;
      }
      console.error("Error fetching tips:", error);
    }
  };

  const handleSave = async (dto: AddTipDTO) => {
    try {
      await postTipAPI(dto, loginToken);
      setIsFormVisible(false);
      loadTips();
    } catch (error) {
      if (error instanceof MissingAuthError) {
        nav("/");
        return;
      }
      console.error("Error saving tip:", error);
    }
  };

  const handlePayment = async () => {
    const payment: CreatePaymentDTO = {
      orderId,
      businessId: loginToken.user.businessId,
      employeeId: employeeId,
      currency,
      orderItems,
    };

    try {
      let response;
      if (paymentMethod === PaymentMethod.CARD) {
        response = await postCardPaymentAPI(payment, loginToken);
      } else {
        response = await postCashPaymentAPI(payment, loginToken);
      }

      const newPayment = {
        paymentId: response.paymentId || response.id
      };

      // Update recentPayments state and save to localStorage
      const updatedPayments = [...recentPayments, newPayment];
      setRecentPayments(updatedPayments);
      localStorage.setItem("recentPayments", JSON.stringify(updatedPayments));

      setPaymentResponse(response);
    } catch (error) {
      console.error("Error processing payment:", error);
      alert("Payment failed. Please try again.");
    }
  };

  const addOrderItem = () => {
    setOrderItems([...orderItems, { orderItemId: "", quantity: 1 }]);
  };

  const updateOrderItem = (index: number, key: keyof OrderItemPaymentDTO, value: string | number) => {
    const updatedOrderItems = [...orderItems];
    updatedOrderItems[index] = {
      ...updatedOrderItems[index],
      [key]: value,
    };
    setOrderItems(updatedOrderItems);
  };

  const removeOrderItem = (index: number) => {
    const updatedOrderItems = orderItems.filter((_, i) => i !== index);
    setOrderItems(updatedOrderItems);
  };

  const goToNextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  const goToPreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage((prevPage) => prevPage - 1);
    }
  };

  const handleRefund = async (paymentId: string) => {
    try {
      const refundResponse = await postInitiateRefundAPI(paymentId, loginToken);

      alert(`Refund initiated successfully for Payment ID: ${paymentId}.`);
      const updatedPayments = recentPayments.filter(payment => payment.paymentId !== paymentId);
      setRecentPayments(updatedPayments);
      localStorage.setItem("recentPayments", JSON.stringify(updatedPayments));
    } catch (error) {
      console.error("Error initiating refund:", error);
      alert(`Failed to initiate refund for Payment ID: ${paymentId}.`);
    }
  };


  return (
      <div className="payment-page">
        <Header />
        <div className="header-container">
          <h1>Payment Tips</h1>
          <div className="add-tip-button-container">
            <button type="button" onClick={() => setIsFormVisible(true)} className="btn btn-primary">
              Add Tip
            </button>
          </div>
        </div>

        {isFormVisible && (
            <Popup setVisibility={isFormVisible}>
              <h1>Add New Tip</h1>
              <TipForm initialData={null} onSubmit={handleSave} onCancel={() => setIsFormVisible(false)} />
            </Popup>
        )}

        <table className="tip-table">
          <thead>
          <tr>
            <th>Order ID</th>
            <th>Amount</th>
            <th>Currency</th>
            <th>Employee</th>
          </tr>
          </thead>
          <tbody>
          {tips.length === 0 ? (
              <tr>
                <td colSpan={4}>No tips available.</td>
              </tr>
          ) : (
              tips.map((tip) => (
                  <tr key={tip.id}>
                    <td>{tip.orderId}</td>
                    <td>{tip.amount}</td>
                    <td>{tip.currency}</td>
                    <td>{tip.employeeId}</td>
                  </tr>
              ))
          )}
          </tbody>
        </table>

        <div className="pagination-controls">
          <button onClick={goToPreviousPage} disabled={currentPage === 0} className="btn btn-secondary">
            Previous
          </button>
          <span>
          Page {currentPage + 1} of {totalPages}
        </span>
          <button onClick={goToNextPage} disabled={currentPage >= totalPages - 1} className="btn btn-secondary">
            Next
          </button>
        </div>

        <div className="payment-container">
          <div className="payment-section">
            <h2>Make a Payment</h2>
            <div>
              <label>
                <input
                    type="radio"
                    name="paymentMethod"
                    value={PaymentMethod.CARD}
                    checked={paymentMethod === PaymentMethod.CARD}
                    onChange={() => setPaymentMethod(PaymentMethod.CARD)}
                />
                Card
              </label>
              <label>
                <input
                    type="radio"
                    name="paymentMethod"
                    value={PaymentMethod.CASH}
                    checked={paymentMethod === PaymentMethod.CASH}
                    onChange={() => setPaymentMethod(PaymentMethod.CASH)}
                />
                Cash
              </label>
            </div>
            <div>
              <input
                  type="text"
                  placeholder="Order ID"
                  value={orderId}
                  onChange={(e) => setOrderId(e.target.value)}
              />
            </div>
            <div>
              <label htmlFor="currency">Currency</label>
              <select id="currency" value={currency} onChange={(e) => setCurrency(e.target.value as unknown as Currency)}>
                <option value={Currency.USD}>USD</option>
                <option value={Currency.EUR}>EUR</option>
              </select>
            </div>
            <div>
              <h3>Order Items</h3>
              {orderItems.map((item, index) => (
                  <div key={index} className="order-item-row">
                    <input
                        type="text"
                        placeholder="Order Item ID"
                        value={item.orderItemId}
                        onChange={(e) => updateOrderItem(index, "orderItemId", e.target.value)}
                    />
                    <input
                        type="number"
                        placeholder="Quantity"
                        value={item.quantity}
                        onChange={(e) => updateOrderItem(index, "quantity", parseInt(e.target.value))}
                    />
                    <button onClick={() => removeOrderItem(index)}>Remove</button>
                  </div>
              ))}
              <button onClick={addOrderItem}>Add Order Item</button>
            </div>
            <button onClick={handlePayment} className="btn btn-primary">
              Submit Payment
            </button>
          </div>

          <div className="recent-payments">
            <h3>Recent Payments</h3>
            {recentPayments.length > 0 ? (
                <table className="recent-payments-table">
                  <thead>
                  <tr>
                    <th>Payment ID</th>
                    <th>Action</th>
                  </tr>
                  </thead>
                  <tbody>
                  {recentPayments.map((payment, index) => (
                      <tr key={index}>
                        <td>{payment.paymentId}</td>
                        <td>
                          <button onClick={() => handleRefund(payment.paymentId)} className="btn btn-danger">
                            Refund
                          </button>
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
            ) : (
                <p>No recent payments.</p>
            )}
          </div>

          {paymentResponse && (
              <div className="payment-response">
                <h3>Payment Response:</h3>
                <pre>{JSON.stringify(paymentResponse, null, 2)}</pre>
              </div>
          )}
        </div>
      </div>
  );
};

export default PaymentPage;
