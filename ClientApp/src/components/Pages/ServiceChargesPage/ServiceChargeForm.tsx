import React, { useState } from "react";
import { Currency, LoginResponseDTO, PostServiceChargeDTO, PricingStrategy, ServiceChargeDTO } from "../../../data/Responses";
import CardComponent from "../../CardComponent";
import { useNavigate } from "react-router";

interface ServiceChargeFormProps {
    initialData?: ServiceChargeDTO | null;
    onSubmit: (data: PostServiceChargeDTO) => void;
    onCancel: () => void;
}

const ServiceChargeForm: React.FC<ServiceChargeFormProps> = ({ initialData, onSubmit, onCancel }) => {
    const nav = useNavigate();

    const [title, setTitle] = useState(initialData?.title || "");
    const [valueType, setValueType] = useState<PricingStrategy>(
        initialData?.valueType ?? PricingStrategy.FIXED_AMOUNT
    );
    const [serviceChargeValue, setServiceChargeValue] = useState(initialData?.serviceChargeValue || 0);
    const [currency, setCurrency] = useState<Currency>(initialData?.currency ?? Currency.USD);
    const [serviceChargeValueError, setServiceChargeValueError] = useState<string | null>(null);
    
      const loginString = localStorage.getItem("loginToken");
      if(!loginString) {
        nav("/");
        return;
      }
      const loginToken: LoginResponseDTO = JSON.parse(loginString);

    const handleSave = () => {
        // Validate serviceChargeValue
        if (serviceChargeValue <= 0) {
            setServiceChargeValueError("Service charge value must be a positive number.");
            return; // Don't submit if validation fails
        } else {
            setServiceChargeValueError(null); // Clear error if validation passes
        }

        const dto: PostServiceChargeDTO = {
            title,
            valueType,
            serviceChargeValue,
            currency,
            businessId: loginToken.user.businessId,
        };

        console.log("Submitting Service Charge:", dto);

        onSubmit(dto);

        // Reset form after submission for create operation
        if (!initialData) {
            setTitle("");
            setValueType(PricingStrategy.FIXED_AMOUNT);
            setServiceChargeValue(0);
            setCurrency(Currency.USD);
        }
    };

    return (
        <CardComponent className="serviceChargeForm">
            <input
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                type="text"
                className="form-control"
                placeholder="Title"
                required
            />
            <select
                value={valueType}
                onChange={(e) => setValueType(Number(e.target.value) as PricingStrategy)}
                className="form-control"
            >
                {Object.entries(PricingStrategy)
                    .filter(([key]) => isNaN(Number(key)))
                    .map(([key, value]) => (
                        <option key={value} value={value}>
                            {key}
                        </option>
                    ))}
            </select>
            <input
                value={serviceChargeValue}
                onChange={(e) => {
                    const value = Number(e.target.value);
                    if (!isNaN(value)) {
                        setServiceChargeValue(value);
                    }
                }}
                type="number"
                className="form-control"
                placeholder="Service Charge Value"
                required
            />
            {serviceChargeValueError && (
                <div className="text-danger" style={{ fontSize: '12px' }}>
                    {serviceChargeValueError}
                </div>
            )}
            <select
                value={currency}
                onChange={(e) => setCurrency(Number(e.target.value) as Currency)}
                className="form-control"
            >
                {Object.entries(Currency)
                    .filter(([key]) => isNaN(Number(key)))
                    .map(([key, value]) => (
                        <option key={value} value={value}>
                            {key}
                        </option>
                    ))}
            </select>
            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <button
                    type="button"
                    onClick={handleSave}
                    className="btn btn-success"
                    disabled={!title || !serviceChargeValue || serviceChargeValue <= 0}
                >
                    Save
                </button>

                <button type="button" onClick={onCancel} className="btn btn-danger">
                    Cancel
                </button>
            </div>
        </CardComponent>
    );
};

export default ServiceChargeForm;
