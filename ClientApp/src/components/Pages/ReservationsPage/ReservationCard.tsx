import React from "react";
import { ReservationDTO } from "../../../data/Responses";
import "./Reservations.css";

interface ReservationCardProps {
    reservation: ReservationDTO;
    onEdit: () => void;
    onDelete: () => void;
}

function ReservationCard({ reservation, onEdit, onDelete }: ReservationCardProps) {
    return (
        <div className="reservationCard">
            <h3>
                {reservation.customer.firstName} {reservation.customer.lastName}
            </h3>
            <p>
                {new Date(reservation.reservationStartAt).toLocaleString()} - {new Date(reservation.reservationEndAt).toLocaleString()}
            </p>
            <button onClick={onEdit} className="btn btn-secondary">
                Edit
            </button>
            <button onClick={onDelete} className="btn btn-danger">
                Delete
            </button>
        </div>
    );
}

export default ReservationCard;
