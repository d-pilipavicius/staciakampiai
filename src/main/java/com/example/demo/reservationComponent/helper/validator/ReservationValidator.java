package com.example.demo.reservationComponent.helper.validator;

import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PutReservationDTO;
import com.example.demo.reservationComponent.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ReservationValidator {

    private static final Logger logger = LoggerFactory.getLogger(ReservationValidator.class);

    private final ReservationRepository reservationRepository;

    public void validatePostReservationDTO(PostReservationDTO dto){
        // Validate if end and start time are correct
        validateDates(dto.getReservationStartAt(), dto.getReservationEndAt());
    }

    private void validateDates(Timestamp start, Timestamp end){
        if(start.after(end)){
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }

    public void validateReservationExists(UUID reservationId) {
        if(!reservationRepository.existsById(reservationId)){
            throw new IllegalArgumentException("Reservation not found");
        }
    }
}
