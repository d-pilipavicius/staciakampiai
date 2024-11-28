package com.example.demo.serviceChargeComponent.domain.services;

import com.example.demo.serviceChargeComponent.helper.mapper.ServiceChargeMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.serviceChargeComponent.api.dtos.GetServiceChargesDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PatchServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ResponseServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.serviceChargeComponent.domain.entities.ServiceCharge;
import com.example.demo.serviceChargeComponent.repository.AppliedServiceChargeRepository;
import com.example.demo.serviceChargeComponent.repository.ServiceChargeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.demo.serviceChargeComponent.api.dtos.PostServiceChargeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceChargeService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceChargeService.class);

    private final ServiceChargeRepository serviceChargeRepository;
    private final AppliedServiceChargeRepository appliedServiceChargeRepository;

    public ServiceChargeService(
            ServiceChargeRepository serviceChargeRepository,
            AppliedServiceChargeRepository appliedServiceChargeRepository
    ) {
        this.serviceChargeRepository = serviceChargeRepository;
        this.appliedServiceChargeRepository = appliedServiceChargeRepository;
    }

    @Transactional
    public ServiceChargeDTO createServiceCharge(PostServiceChargeDTO postServiceChargeDTO){
        return Mapper.mapToDTO(
                serviceChargeRepository.save(
                        Mapper.mapToModel(
                                postServiceChargeDTO,
                                ServiceChargeMapper.TO_MODEL
                        )
                ),
                ServiceChargeMapper.TO_DTO
        );
    }

    public GetServiceChargesDTO getServiceCharges(){
        List<ServiceChargeDTO> serviceCharges = serviceChargeRepository.findAll().stream()
                .map(ServiceChargeMapper.TO_DTO::map)
                .toList();

        return new GetServiceChargesDTO(serviceCharges, serviceCharges.size());
    }

    public GetServiceChargesDTO getServiceCharges(int page, int size){
        List<ServiceChargeDTO> serviceCharges = serviceChargeRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ServiceChargeMapper.TO_DTO::map)
                .toList();

        return new GetServiceChargesDTO(
                serviceCharges.size(),
                size,
                page,
                serviceCharges
        );
    }

    @Transactional
    public ResponseServiceChargeDTO updateServiceCharge(PatchServiceChargeDTO patchServiceChargeDTO, UUID id) {
        ServiceCharge serviceCharge = serviceChargeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ServiceCharge with id " + id + " not found"));

        applyServiceChargeUpdates(patchServiceChargeDTO, serviceCharge);

        ServiceCharge updatedServiceCharge = serviceChargeRepository.save(serviceCharge);
        return new ResponseServiceChargeDTO(Mapper.mapToDTO(updatedServiceCharge, ServiceChargeMapper.TO_DTO));
    }

    public void deleteServiceCharge(UUID id) {
        if (!serviceChargeRepository.existsById(id)) {
            logger.error("ServiceCharge with id {} not found", id);
            throw new IllegalArgumentException("ServiceCharge with id " + id + " not found");
        }
        serviceChargeRepository.deleteById(id);
    }

    private void applyServiceChargeUpdates(PatchServiceChargeDTO patchServiceChargeDTO, ServiceCharge serviceCharge) {
        patchServiceChargeDTO.getTitle().ifPresent(serviceCharge::setTitle);
        patchServiceChargeDTO.getValue().ifPresent(serviceCharge::setValue);
        patchServiceChargeDTO.getValueType().ifPresent(serviceCharge::setValueType);
        patchServiceChargeDTO.getCurrency().ifPresent(serviceCharge::setCurrency);
    }
}
