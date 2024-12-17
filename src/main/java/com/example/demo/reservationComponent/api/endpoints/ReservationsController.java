package com.example.demo.reservationComponent.api.endpoints;

import com.example.demo.reservationComponent.api.dtos.GetReservationsDTO;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PutReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.applicationServices.ReservationApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/reservations")
@AllArgsConstructor
public class ReservationsController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationsController.class);
    private final ReservationApplicationService reservationApplicationService;

    @PostMapping
    public ResponseEntity<Object> createReservation(
            @RequestParam UUID employeeId,
            @Valid @RequestBody PostReservationDTO postReservationDTO
    ) {
        ReservationDTO reservationDTO = reservationApplicationService.createReservation(postReservationDTO, employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getReservations(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam UUID businessId
    ){
        GetReservationsDTO reservationsDTO = reservationApplicationService.getReservationsByBusinessId(businessId, pageNumber, pageSize);
        return ResponseEntity.ok(reservationsDTO);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Object> updateReservation(
            @PathVariable UUID reservationId,
            @Valid @RequestBody PutReservationDTO putReservationDTO
    ){
        ReservationDTO updatedReservation = reservationApplicationService.updateReservation(putReservationDTO, reservationId);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Object> deleteReservation(
            @PathVariable UUID reservationId
    ){
        reservationApplicationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
