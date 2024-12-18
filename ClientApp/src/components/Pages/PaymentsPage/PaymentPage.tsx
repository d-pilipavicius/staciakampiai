import { useEffect, useState } from "react";
import { postTipAPI, getTipsAPI } from "../../../data/APICalls"; // Import your getOrderTipsAPI
import { AddTipDTO, TipDTO, LoginResponseDTO, GetTipDTO } from "../../../data/Responses";
import TipForm from "./TipForm";
import Header from "../../Header";
import "./paymentPage.css";
import Popup from "../../Popup";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";

const PaymentPage: React.FC = () => {
  const nav = useNavigate();

  const [tips, setTips] = useState<TipDTO[]>([]);
  const [selectedTip, setSelectedTip] = useState<TipDTO | null>(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const pageSize = 10;
  const [isFormVisible, setIsFormVisible] = useState(false);

  const loginString = localStorage.getItem("loginToken");
  if (!loginString) {
    nav("/");
    return null;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  // Fetch tips
  useEffect(() => {
    loadTips();
  }, [currentPage]);

  const loadTips = async () => {
    try {
      const data: GetTipDTO = await getTipsAPI(
          currentPage,
          pageSize,
          loginToken
      );
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
      // Post the new tip but do not update the table state
      await postTipAPI(dto, loginToken);
      setIsFormVisible(false);  // Close the form
      loadTips();  // Reload the tips after saving
    } catch (error) {
      if (error instanceof MissingAuthError) {
        nav("/");
        return;
      }
      console.error("Error saving tip:", error);
    }
  };

  const mapTipDTOToAddTipDTO = (tip: TipDTO): AddTipDTO => ({
    orderId: tip.orderId,
    employeeId: tip.employeeId,
    amount: tip.amount,
    currency: tip.currency,
  });

  // Pagination controls
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

  return (
      <div className="payment-page">
        <Header />

        <div className="header-container">
          <h1>Payment Tips</h1>

          {/* Add Tip Button */}
          <div className="add-tip-button-container">
            <button
                type="button"
                onClick={() => {
                  setSelectedTip(null); // Reset selected tip to create a new one
                  setIsFormVisible(true); // Show the form
                }}
                className="btn btn-primary"
            >
              Add Tip
            </button>
          </div>
        </div>

        {/* Add Tip Form */}
        {isFormVisible && (
            <Popup setVisibility={isFormVisible}>
              <h1>{selectedTip ? "Edit Tip" : "Add New Tip"}</h1>
              <TipForm
                  initialData={selectedTip ? mapTipDTOToAddTipDTO(selectedTip) : null}
                  onSubmit={handleSave}
                  onCancel={() => setIsFormVisible(false)}
              />
            </Popup>
        )}

        {/* Tip Table */}
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

        {/* Pagination Controls */}
        <div className="pagination-controls">
          <button
              onClick={goToPreviousPage}
              disabled={currentPage === 0}
              className="btn btn-secondary"
          >
            Previous
          </button>
          <span>
          Page {currentPage + 1} of {totalPages}
        </span>
          <button
              onClick={goToNextPage}
              disabled={currentPage >= totalPages - 1}
              className="btn btn-secondary"
          >
            Next
          </button>
        </div>
      </div>
  );
};

export default PaymentPage;
