import { useEffect, useState } from "react";
import {GetReservationsDTO, PostReservationDTO, LoginResponseDTO, PutReservationDTO} from "../../../data/Responses";
import Header from "../../Header";
import {deleteReservationAPI, getReservationsAPI, postReservationAPI, putReservationAPI} from "../../../data/APICalls";
import Pageination from "../../Pageination";
import ReservationCard from "./ReservationCard";
import Popup from "../../Popup";
import { useNavigate } from "react-router";
import { MissingAuthError } from "../../../data/MissingAuthError";
import "./Reservations.css";

const pageSize = 20;

function ReservationsPage() {
    const nav = useNavigate();

    const [trigger, setTrigger] = useState(0);
    const [startAt, setStartAt] = useState("");
    const [endAt, setEndAt] = useState("");
    const [customer, setCustomer] = useState({ firstName: "", lastName: "", phoneNumber: "" });
    const [editReservationId, setEditReservationId] = useState<string | null>(null);

    const [isVisible, setVisibility] = useState(false);
    const [reservations, setReservations] = useState<GetReservationsDTO | null>();
    const [page, setPage] = useState(1);

    const loginString = localStorage.getItem("loginToken");
    if (!loginString) {
        nav("/");
        return null;
    }
    const loginToken: LoginResponseDTO = JSON.parse(loginString);

    // Fetch reservations
    const getReservations = async () => {
        try {
            const reservationsDTO = await getReservationsAPI(loginToken.user.businessId, page - 1, pageSize, loginToken);
            setReservations(reservationsDTO);
        } catch (err) {
            if (err instanceof MissingAuthError) {
                nav("/");
            } else {
                throw err;
            }
        }
    };

    useEffect(() => {
        getReservations();
    }, [trigger]);

    const onSetPage = (id: number) => {
        setPage(id);
        setTrigger(trigger + 1);
    };

    const openPopup = (reservation?: any) => {
        if (reservation) {
            // Edit mode: Populate fields with reservation data
            setStartAt(new Date(reservation.reservationStartAt).toISOString().slice(0, 16));
            setEndAt(new Date(reservation.reservationEndAt).toISOString().slice(0, 16));
            setCustomer({
                firstName: reservation.customer.firstName,
                lastName: reservation.customer.lastName,
                phoneNumber: reservation.customer.phoneNumber,
            });
            setEditReservationId(reservation.id);
        } else {
            // Create mode: Clear fields
            setStartAt("");
            setEndAt("");
            setCustomer({ firstName: "", lastName: "", phoneNumber: "" });
            setEditReservationId(null);
        }
        setVisibility(true);
    };

    const closePopup = () => {
        setVisibility(false);
    };

    const handleSaveReservation = async () => {
        if (!startAt || !endAt || !customer.firstName || !customer.lastName || !customer.phoneNumber) {
            alert("All fields are required!");
            return;
        }

        try {
            if (editReservationId) {
                // Update reservation
                const updatedReservation: PutReservationDTO = {
                    reservationStartAt: new Date(startAt).toISOString(),
                    reservationEndAt: new Date(endAt).toISOString(),
                    customer: {
                        firstName: customer.firstName,
                        lastName: customer.lastName,
                        phoneNumber: customer.phoneNumber,
                    },
                };

                await putReservationAPI(editReservationId, updatedReservation, loginToken);
            } else {
                // Create new reservation
                const newReservation: PostReservationDTO = {
                    businessId: loginToken.user.businessId,
                    reservationStartAt: new Date(startAt).toISOString(),
                    reservationEndAt: new Date(endAt).toISOString(),
                    customer: {
                        firstName: customer.firstName,
                        lastName: customer.lastName,
                        phoneNumber: customer.phoneNumber,
                    },
                    serviceChargeIds: [],
                };

                await postReservationAPI(newReservation, loginToken);
            }

            closePopup();
            getReservations();
        } catch (err) {
            if (err instanceof MissingAuthError) {
                nav("/");
            } else {
                // @ts-ignore
                alert(`Error: ${err.message}`);
            }
        }
    };

    const handleDeleteReservation = async (reservationId: string) => {
        if (window.confirm("Are you sure you want to delete this reservation?")) {
            try {
                await deleteReservationAPI(reservationId, loginToken);
                getReservations();
            } catch (err) {
                // @ts-ignore
                alert(`Error: ${err.message}`);
            }
        }
    };

    return (
        <>
            <Header />
            <Popup setVisibility={isVisible}>
                <h1>{editReservationId ? "Edit Reservation" : "Create New Reservation"}</h1>
                <input value={startAt} onChange={(e) => setStartAt(e.target.value)} type="datetime-local" className="form-control" placeholder="Start Time" />
                <input value={endAt} onChange={(e) => setEndAt(e.target.value)} type="datetime-local" className="form-control" placeholder="End Time" />
                <input
                    value={customer.firstName}
                    onChange={(e) => setCustomer({ ...customer, firstName: e.target.value })}
                    type="text"
                    className="form-control"
                    placeholder="First Name"
                />
                <input
                    value={customer.lastName}
                    onChange={(e) => setCustomer({ ...customer, lastName: e.target.value })}
                    type="text"
                    className="form-control"
                    placeholder="Last Name"
                />
                <input
                    value={customer.phoneNumber}
                    onChange={(e) => setCustomer({ ...customer, phoneNumber: e.target.value })}
                    type="text"
                    className="form-control"
                    placeholder="Phone Number"
                />
                <button type="button" onClick={handleSaveReservation} className="btn btn-success">
                    Save
                </button>
                <button type="button" onClick={closePopup} className="btn btn-danger">
                    Cancel
                </button>
            </Popup>
            <div className="reservationPage">
                <h1>Reservations</h1>
                <button type="button" onClick={() => openPopup()} className="btn btn-primary">
                    Add Reservation
                </button>
                {reservations && reservations.totalItems > 0 ? (
                    reservations.items.map((item) => (
                        <ReservationCard
                            key={item.id}
                            reservation={item}
                            onEdit={() => openPopup(item)}
                            onDelete={() => handleDeleteReservation(item.id)}
                        />
                    ))
                ) : (
                    <p>No reservations available</p>
                )}
                <Pageination selectedPage={page} totalPages={reservations ? reservations.totalPages : 0} setPage={onSetPage} />
            </div>
        </>
    );
}

export default ReservationsPage;