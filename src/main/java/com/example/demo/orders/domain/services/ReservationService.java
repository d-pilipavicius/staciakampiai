package com.example.demo.orders.domain.services;

import com.example.demo.helper.mapper.CustomerMapper;
import com.example.demo.helper.mapper.ReservationMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orders.API.DTOs.ReservationDTOs.GetReservationsDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.PatchReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ResponseReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.PostReservationDTO;
import com.example.demo.orders.domain.entities.Reservation;
import com.example.demo.orders.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // TODO: change how the DTO is being mapped, reservation shouldn't receive a list of serviceChargeIds, appliedCharges should be created separately?
    // TODO: galima movint i application service ir is ten kniest sita ir applied services atskirai
    public void createReservation(PostReservationDTO postReservationDTO){
        reservationRepository.save(
                Mapper.mapToModel(
                        postReservationDTO,
                        ReservationMapper.TO_MODEL
                )
        );
    }

    public GetReservationsDTO getReservations() {
        List<FullReservation> reservations = reservationRepository.findAll().stream()
                .map(ReservationMapper.TO_DTO::map)
                .toList();

        GetReservationsDTO getReservationsDTO = new GetReservationsDTO();
        getReservationsDTO.setItems(reservations);

        return getReservationsDTO;
    }

    public GetReservationsDTO getReservations(int page, int size) {
        List<FullReservation> reservations = reservationRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ReservationMapper.TO_DTO::map)
                .toList();

        GetReservationsDTO getReservationsDTO = new GetReservationsDTO();
        getReservationsDTO.setItems(reservations);
        getReservationsDTO.setCurrentPage(page);
        getReservationsDTO.setTotalItems(reservations.size());
        getReservationsDTO.setTotalPages(size);

        return getReservationsDTO;
    }

    @Transactional
    public ResponseReservationDTO updateReservation(PatchReservationDTO patchReservationDTO, UUID id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (reservation.isEmpty()) {
            throw new EntityNotFoundException("Reservation not found");
        }

        patchReservationDTO.getReservationStartAt().ifPresent(reservation.get()::setReservationStartAt);
        patchReservationDTO.getReservationEndAt().ifPresent(reservation.get()::setReservationEndAt);
        patchReservationDTO.getCustomer().ifPresent(
                customerDTO -> reservation.get().setCustomer(
                        Mapper.mapToModel(
                                customerDTO,
                                CustomerMapper.TO_MODEL
                        )
                )
        );

        ResponseReservationDTO updatedReservation = new ResponseReservationDTO();
        updatedReservation.setReservation(Mapper.mapToDTO(
                reservationRepository.save(reservation.get()),
                ReservationMapper.TO_DTO
        ));

        return updatedReservation;
    }

    public void deleteReservation(UUID id) {
        reservationRepository.deleteById(id);
    }
}
