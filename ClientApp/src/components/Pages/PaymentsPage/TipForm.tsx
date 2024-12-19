import { useState } from "react";
import { AddTipDTO, Currency } from "../../../data/Responses";

interface TipFormProps {
    initialData?: AddTipDTO | null;
    onSubmit: (dto: AddTipDTO) => void;
    onCancel: () => void;
}

const TipForm: React.FC<TipFormProps> = ({ initialData, onSubmit, onCancel }) => {
    const [orderId, setOrderId] = useState(initialData?.orderId || "");
    const [employeeId, setEmployeeId] = useState(initialData?.employeeId || "8f5a6b4c-3245-2345-4567-9876543210ab");
    const [amount, setAmount] = useState(initialData?.amount || 0);
    const [currency, setCurrency] = useState<Currency>(initialData?.currency || Currency.USD);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit({ orderId, employeeId, amount, currency });
    };

    return (
        <form className="tip-form" onSubmit={handleSubmit}>
            <div className="form-group">
                <label htmlFor="orderId">Order ID</label>
                <input
                    type="text"
                    id="orderId"
                    value={orderId}
                    onChange={(e) => setOrderId(e.target.value)}
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="amount">Amount</label>
                <input
                    type="number"
                    id="amount"
                    value={amount}
                    onChange={(e) => setAmount(Number(e.target.value))}
                    min="0"
                    step="0.01"
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="currency">Currency</label>
                <select
                    id="currency"
                    value={currency}
                    onChange={(e) => setCurrency(e.target.value as unknown as Currency)}
                    required
                >
                    <option value={Currency.USD}>USD</option>
                    <option value={Currency.EUR}>EUR</option>
                    {/* Add more currency options as needed */}
                </select>
            </div>

            <div className="form-actions">
                <button type="submit" className="btn btn-primary">
                    Save Tip
                </button>
                <button type="button" className="btn btn-secondary" onClick={onCancel}>
                    Cancel
                </button>
            </div>
        </form>
    );
};

export default TipForm;