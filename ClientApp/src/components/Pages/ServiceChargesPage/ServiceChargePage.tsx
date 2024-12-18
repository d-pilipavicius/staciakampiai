import { useEffect, useState } from "react";
import {
  getServiceChargesAPI,
  postServiceChargeAPI,
  putServiceChargeAPI,
  deleteServiceChargeAPI,
} from "../../../data/APICalls";
import {
  ServiceChargeDTO,
  PostServiceChargeDTO,
  GetServiceChargeDTO,
  PutServiceChargeDTO,
  LoginResponseDTO,
} from "../../../data/Responses";
import ServiceChargeForm from "./ServiceChargeForm";
import Header from "../../Header";
import "./serviceCharge.css";
import Popup from "../../Popup"; // Import Popup component
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";

const ServiceChargePage: React.FC = () => {
  const nav = useNavigate();

  const [serviceCharges, setServiceCharges] = useState<ServiceChargeDTO[]>([]);
  const [selectedServiceCharge, setSelectedServiceCharge] = useState<ServiceChargeDTO | null>(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const pageSize = 10;
  const [isFormVisible, setIsFormVisible] = useState(false);

  const loginString = localStorage.getItem("loginToken");
  if(!loginString) {
    nav("/");
    return;
  }
  const loginToken: LoginResponseDTO = JSON.parse(loginString);

  useEffect(() => {
    loadServiceCharges();
  }, [currentPage]);

  // Fetch service charges
  const loadServiceCharges = async () => {
    try {
      const data: GetServiceChargeDTO = await getServiceChargesAPI(currentPage, pageSize, loginToken);
      setServiceCharges(data.items);
      setTotalPages(data.totalPages);
    } catch (error) {
      if(error instanceof MissingAuthError) {
        nav("/");
        return;
      }
      console.error("Error fetching service charges:", error);
    }
  };

  // Handle creation or update of service charge
  const handleSave = async (dto: PostServiceChargeDTO | PutServiceChargeDTO) => {
    try {
      if (selectedServiceCharge) {
        await putServiceChargeAPI(selectedServiceCharge.id, dto as PutServiceChargeDTO, loginToken);
      } else {
        await postServiceChargeAPI(dto as PostServiceChargeDTO, loginToken);
      }
      loadServiceCharges();
      setSelectedServiceCharge(null);
      setIsFormVisible(false); // Close form after saving
    } catch (error) {
      if(error instanceof MissingAuthError) {
        nav("/");
        return;
      }
      console.error("Error saving service charge:", error);
    }
  };

  // Handle deletion
  const handleDelete = async (id: string) => {
    try {
      await deleteServiceChargeAPI(id, loginToken);
      loadServiceCharges();
    } catch (error) {
      if(error instanceof MissingAuthError) {
        nav("/");
        return;
      }
      console.error("Error deleting service charge:", error);
    }
  };

  // Pagination navigation
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
      <div className="service-charge-page">
        <Header />

        <div className="header-container">
          <h1>Service Charges</h1>

          {/* Add Service Charge Button */}
          <div className="add-service-charge-button-container">
            <button
                type="button"
                onClick={() => {
                  setSelectedServiceCharge(null); // Reset selected service charge to create a new one
                  setIsFormVisible(true); // Show the form
                }}
                className="btn btn-primary"
            >
              Add Service Charge
            </button>
          </div>
        </div>


        {/* Add Service Charge Form */}
        {isFormVisible && (
            <Popup setVisibility={isFormVisible}>
              <h1>{selectedServiceCharge ? "Edit Service Charge" : "Add New Service Charge"}</h1>
              <ServiceChargeForm
                  initialData={selectedServiceCharge}
                  onSubmit={handleSave}
                  onCancel={() => setIsFormVisible(false)} // Close the form when canceled
              />
            </Popup>
        )}

        {/* Service Charge Table */}
        <table className="service-charge-table">
          <thead>
          <tr>
            <th>Title</th>
            <th>Value Type</th>
            <th>Service Charge Value</th>
            <th>Currency</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          {serviceCharges.length === 0 ? (
              <tr>
                <td colSpan={5}>No service charges available.</td>
              </tr>
          ) : (
              serviceCharges.map((charge) => (
                  <tr key={charge.id}>
                    <td>{charge.title}</td>
                    <td>{charge.valueType}</td>
                    <td>{charge.serviceChargeValue}</td>
                    <td>{charge.currency}</td>
                    <td>
                      <button
                          onClick={() => {
                            setSelectedServiceCharge(charge); // Populate form with selected service charge for editing
                            setIsFormVisible(true); // Show the form to edit
                          }}
                          className="btn btn-primary"
                      >
                        Edit
                      </button>
                      <button
                          onClick={() => handleDelete(charge.id)}
                          className="btn btn-danger"
                      >
                        Delete
                      </button>
                    </td>
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

export default ServiceChargePage;
