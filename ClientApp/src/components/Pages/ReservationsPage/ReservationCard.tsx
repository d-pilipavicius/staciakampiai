import React from "react";
import { ReservationDTO } from "../../../data/Responses";
import "./Reservations.css";


function ReservationCard({ reservation, updatePage }: { reservation: ReservationDTO; updatePage: () => void }) {
    return (
        <div className="reservationCard">
            <h3>Reservation for {reservation.customer.firstName} {reservation.customer.lastName}</h3>
            <p>Start: {new Date(reservation.reservationStartAt).toLocaleString()}</p>
            <p>End: {new Date(reservation.reservationEndAt).toLocaleString()}</p>
            <p>Phone: {reservation.customer.phoneNumber}</p>
        </div>
    );
}

export default ReservationCard;
