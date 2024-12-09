package com.example.demo.reservationComponent.domain.services;

import com.example.demo.reservationComponent.helper.mapper.CustomerMapper;
import com.example.demo.reservationComponent.helper.mapper.ReservationMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.reservationComponent.api.dtos.GetReservationsDTO;
import com.example.demo.reservationComponent.api.dtos.PatchReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ResponseReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.serviceChargeComponent.domain.entities.AppliedServiceCharge;
import com.example.demo.reservationComponent.domain.entities.Reservation;
import com.example.demo.serviceChargeComponent.repository.AppliedServiceChargeRepository;
import com.example.demo.reservationComponent.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public ReservationDTO createReservation(PostReservationDTO postReservationDTO){
        Reservation reservation = Mapper.mapToModel(
                postReservationDTO,
                ReservationMapper.TO_MODEL
        );

        postReservationDTO.getServiceChargeIds()
                .ifPresent(scIds -> {
                    List<AppliedServiceCharge> appliedServiceCharges = getAppliedServiceChargesByIds(scIds);
                    appliedServiceCharges.forEach(sc -> sc.setReservation(reservation));
                    reservation.setAppliedServiceCharges(appliedServiceCharges);
                });

        return Mapper.mapToDTO(
                reservationRepository.save(reservation),
                ReservationMapper.TO_DTO
        );
    }

    public GetReservationsDTO getReservations() {
        List<ReservationDTO> reservations = reservationRepository.findAll().stream()
                .map(ReservationMapper.TO_DTO::map)
                .toList();

        return new GetReservationsDTO(reservations, reservations.size());
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
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        applyReservationUpdates(patchReservationDTO, reservation);

        Reservation updatedReservation = reservationRepository.save(reservation);
        return new ResponseReservationDTO(Mapper.mapToDTO(
                updatedReservation,
                ReservationMapper.TO_DTO
        ));
    }

    public void deleteReservation(UUID id) {
        reservationRepository.deleteById(id);
    }

    private List<AppliedServiceCharge> getAppliedServiceChargesByIds(List<UUID> serviceChargeIds) {
        List<AppliedServiceCharge> appliedServiceCharges = appliedServiceChargeRepository.findAllById(serviceChargeIds);
        if (appliedServiceCharges.size() != serviceChargeIds.size()) {
            throw new IllegalArgumentException("Some of the service charges are not found");
        }
        return appliedServiceCharges;
    }

    private void applyReservationUpdates(PatchReservationDTO patchReservationDTO, Reservation reservation) {
        patchReservationDTO.getReservationStartAt().ifPresent(reservation::setReservationStartAt);
        patchReservationDTO.getReservationEndAt().ifPresent(reservation::setReservationEndAt);
        patchReservationDTO.getCustomer()
                .ifPresent(customerDTO -> reservation.setCustomer(
                        Mapper.mapToModel(customerDTO, CustomerMapper.TO_MODEL)
                ));
    }
}
