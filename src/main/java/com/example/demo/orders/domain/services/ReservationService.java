package com.example.demo.orders.domain.services;

import com.example.demo.helper.mapper.CustomerMapper;
import com.example.demo.helper.mapper.ReservationMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orders.API.DTOs.ReservationDTOs.GetReservationsDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.PatchReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ResponseReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.PostReservationDTO;
import com.example.demo.orders.domain.entities.AppliedServiceCharge;
import com.example.demo.orders.domain.entities.Reservation;
import com.example.demo.orders.repository.AppliedServiceChargeRepository;
import com.example.demo.orders.repository.ReservationRepository;
import com.example.demo.orders.repository.ServiceChargeRepository;
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
    private final AppliedServiceChargeRepository appliedServiceChargeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            AppliedServiceChargeRepository appliedServiceChargeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.appliedServiceChargeRepository = appliedServiceChargeRepository;
    }

    // TODO: change how the DTO is being mapped, reservation shouldn't receive a list of serviceChargeIds, appliedCharges should be created separately?
    // TODO: galima movint i application service ir is ten kniest sita ir applied services atskirai
    @Transactional
    public ReservationDTO createReservation(PostReservationDTO postReservationDTO){
        Reservation reservation = Mapper.mapToModel(
                postReservationDTO,
                ReservationMapper.TO_MODEL
        );

        if(postReservationDTO.getServiceChargeIds().isPresent()){
            List<AppliedServiceCharge> appliedServiceCharges = appliedServiceChargeRepository
                    .findAllById(
                            postReservationDTO.getServiceChargeIds().get()
                    );

            if(appliedServiceCharges.size() != postReservationDTO.getServiceChargeIds().get().size()){
                throw new IllegalArgumentException("Some of the service charges are not found");
            }

            appliedServiceCharges.forEach(sc -> sc.setReservation(reservation));
            reservation.setAppliedServiceCharges(appliedServiceCharges);
        }

        return Mapper.mapToDTO(
                reservationRepository.save(reservation),
                ReservationMapper.TO_DTO
        );
    }

    public GetReservationsDTO getReservations() {
        List<ReservationDTO> reservations = reservationRepository.findAll().stream()
                .map(ReservationMapper.TO_DTO::map)
                .toList();

        GetReservationsDTO getReservationsDTO = new GetReservationsDTO();
        getReservationsDTO.setItems(reservations);

        return getReservationsDTO;
    }

    public GetReservationsDTO getReservations(int page, int size) {
        List<ReservationDTO> reservations = reservationRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ReservationMapper.TO_DTO::map)
                .toList();

        return new GetReservationsDTO(
                reservations.size(),
                size,
                page,
                reservations
        );
    }

    @Transactional
    public ResponseReservationDTO updateReservation(PatchReservationDTO patchReservationDTO, UUID id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found");
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
