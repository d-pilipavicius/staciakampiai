package com.example.demo.reservationComponent.helper.validator;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnprocessableException;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PutReservationDTO;
import com.example.demo.reservationComponent.domain.entities.Reservation;
import com.example.demo.reservationComponent.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@AllArgsConstructor
@Component
public class ReservationValidator {

    private static final Logger logger = LoggerFactory.getLogger(ReservationValidator.class);

    private final ReservationRepository reservationRepository;

    public void validatePostReservationDTO(PostReservationDTO dto){
        validateDates(dto.getReservationStartAt(), dto.getReservationEndAt());
        validatePhoneNumber(dto.getCustomer().getPhoneNumber());
        validateTimeSlotWorksForOtherRezervations(dto.getBusinessId(), dto.getReservationStartAt(), dto.getReservationEndAt());
    }

    public void validateTimeSlotWorksForOtherRezervations(UUID businessId, Timestamp startTime, Timestamp endTime){
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservationsForPut(
                businessId,
                startTime,
                endTime
        );

        if(!overlappingReservations.isEmpty()){
            throw new UnprocessableException(
                    "THe provided start " + startTime + " and end " + endTime + " times, are overlapping with existing reservations."
            );
        }
    }

    private void validateDates(Timestamp start, Timestamp end){
        if(start.after(end)){
            throw new UnprocessableException("Start date cannot be after end date");
        }
    }

    public void validateReservationExists(UUID reservationId) {
        if(!reservationRepository.existsById(reservationId)){
            throw new UnprocessableException("Reservation not found");
        }
    }

    public void validatePutReservationDTO(UUID businessId, PutReservationDTO putReservationDTO, UUID reservationId) {
        validateDates(putReservationDTO.getReservationStartAt(), putReservationDTO.getReservationEndAt());
        validatePhoneNumber(putReservationDTO.getCustomer().getPhoneNumber());
        validateTimeSlotWorksForOtherRezervations(businessId, putReservationDTO.getReservationStartAt(), putReservationDTO.getReservationEndAt(), reservationId);
    }

    public void validateTimeSlotWorksForOtherRezervations(UUID businessId, Timestamp startTime, Timestamp endTime, UUID reservationId){
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservationsForPut(
                businessId,
                startTime,
                endTime,
                reservationId
        );

        if(!overlappingReservations.isEmpty()){
            throw new UnprocessableException(
                    "THe provided start " + startTime + " and end " + endTime + " times, are overlapping with existing reservations."
            );
        }
    }

    private void validatePhoneNumber(String phoneNumber){
        if(!Pattern.matches("^[+]{1}(?:[0-9\\-()/.]\\s?){6,15}[0-9]{1}$", phoneNumber)){
            throw new UnprocessableException("Phone number is not valid");
        }
    }
}
