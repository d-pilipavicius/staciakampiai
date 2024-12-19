package com.example.demo.reservationComponent.domain.services;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.FullNameDTO;
import com.example.demo.reservationComponent.domain.entities.Customer;
import com.example.demo.reservationComponent.helper.mapper.CustomerMapper;
import com.example.demo.reservationComponent.helper.mapper.ReservationMapper;
import com.example.demo.CommonHelper.mapper.base.Mapper;
import com.example.demo.reservationComponent.api.dtos.GetReservationsDTO;
import com.example.demo.reservationComponent.api.dtos.PutReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.domain.entities.Reservation;
import com.example.demo.reservationComponent.helper.validator.ReservationValidator;
import com.example.demo.reservationComponent.repository.CustomerRepository;
import com.example.demo.reservationComponent.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;

    private final CustomerRepository customerRepository;
    private final ReservationValidator reservationValidator;

    @Transactional
    public ReservationDTO createReservation(PostReservationDTO postReservationDTO, UUID employeeId) {
        Reservation reservation = Mapper.mapToModel(
                postReservationDTO,
                ReservationMapper.TO_MODEL
        );

        reservation.setEmployeeId(employeeId);

        Customer customer = customerRepository.save(reservation.getCustomer());
        reservation.setCustomer(customer);
        Reservation savedReservation = reservationRepository.save(reservation);
        ReservationDTO mappedReservation = Mapper.mapToDTO(savedReservation, ReservationMapper.TO_DTO);

        logger.info("Created Reservation: {}", mappedReservation.toString());
        return mappedReservation;
    }

    public GetReservationsDTO getReservationsByBusinessId(UUID businessId, int page, int size) {
        Page<Reservation> reservations = reservationRepository.findAllByBusinessId(businessId, PageRequest.of(page, size));
        return Mapper.mapToDTO(reservations, ReservationMapper.PAGE_TO_DTO);
    }

    public GetReservationsDTO getActiveReservationsByFullName(FullNameDTO fullNameDTO, int page, int size) {
        Page<Reservation> reservations = reservationRepository.findAllByCustomerFullName(
                fullNameDTO.getFirstName(),
                fullNameDTO.getLastName(),
                PageRequest.of(page, size)
        );
        return Mapper.mapToDTO(reservations, ReservationMapper.PAGE_TO_DTO);
    }

    @Transactional
    public ReservationDTO updateReservation(PutReservationDTO putReservationDTO, UUID reservationId, UUID businessId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        reservationValidator.validatePutReservationDTO(businessId, putReservationDTO, reservationId);

        applyReservationUpdates(putReservationDTO, reservation);

        Reservation updatedReservation = reservationRepository.save(reservation);
        ReservationDTO updatedReservationDTO = Mapper.mapToDTO(updatedReservation, ReservationMapper.TO_DTO);
        logger.info("Updated Reservation with ID: {}, Details: {}", updatedReservation.getId(), updatedReservationDTO.toString());
        return updatedReservationDTO;
    }

    public void deleteReservation(UUID reservationId) {
        // Validate if reservation exists
        reservationValidator.validateReservationExists(reservationId);
        reservationRepository.deleteById(reservationId);
    }

    private void applyReservationUpdates(PutReservationDTO putReservationDTO, Reservation reservation) {
        reservation.setReservationEndAt(putReservationDTO.getReservationEndAt());
        reservation.setReservationStartAt(putReservationDTO.getReservationStartAt());
        reservation.getCustomer().setFirstName(putReservationDTO.getCustomer().getFirstName());
        reservation.getCustomer().setLastName(putReservationDTO.getCustomer().getLastName());
        reservation.getCustomer().setPhoneNumber(putReservationDTO.getCustomer().getPhoneNumber());
        reservation.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    }
}
