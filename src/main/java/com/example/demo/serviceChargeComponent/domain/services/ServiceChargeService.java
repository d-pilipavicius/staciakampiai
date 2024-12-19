package com.example.demo.serviceChargeComponent.domain.services;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.serviceChargeComponent.api.dtos.GetServiceChargesDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PostServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PutServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.serviceChargeComponent.domain.entities.ServiceCharge;
import com.example.demo.serviceChargeComponent.helper.mapper.ServiceChargeMapper;
import com.example.demo.serviceChargeComponent.helper.validator.ServiceChargeValidator;
import com.example.demo.serviceChargeComponent.repository.ServiceChargeRepository;

import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class ServiceChargeService {

    private final ServiceChargeRepository serviceChargeRepository;
    private final ServiceChargeMapper serviceChargeMapper;

    private final ServiceChargeValidator serviceChargeValidator;

    public ServiceChargeDTO createServiceCharge(PostServiceChargeDTO postServiceChargeDTO) {
        serviceChargeValidator.checkPricingStrategy(postServiceChargeDTO.getCurrency(), postServiceChargeDTO.getValueType());
        ServiceCharge serviceCharge = serviceChargeMapper.toServiceCharge(postServiceChargeDTO);
        ServiceCharge savedServiceCharge = serviceChargeRepository.save(serviceCharge);
        return serviceChargeMapper.toServiceChargeDTO(savedServiceCharge);
    }

    public GetServiceChargesDTO getServiceCharges(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ServiceCharge> serviceChargePage = serviceChargeRepository.findAll(pageable);
        serviceChargeValidator.checkIfBusinessHadServiceCharges((int) serviceChargePage.getTotalElements());
        serviceChargeValidator.checkIfServiceChargePageExceeded(page, serviceChargePage.getTotalPages());
        return serviceChargeMapper.toGetServiceChargesDTO(serviceChargePage);
    }
    
    public ServiceChargeDTO updateServiceCharge(PutServiceChargeDTO putServiceChargeDTO, UUID id) {
        ServiceCharge serviceCharge = serviceChargeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ServiceCharge with id " + id + " not found"));
        applyServiceChargeUpdates(putServiceChargeDTO, serviceCharge);
        serviceChargeValidator.checkPricingStrategy(serviceCharge.getCurrency(), serviceCharge.getValueType());
        ServiceCharge updatedServiceCharge = serviceChargeRepository.save(serviceCharge);
        return serviceChargeMapper.toServiceChargeDTO(updatedServiceCharge);
    }


    public void deleteServiceCharge(UUID id) {
        if (serviceChargeRepository.existsById(id)) {
            serviceChargeRepository.deleteById(id);
        }else{
            throw new NotFoundException("ServiceCharge with id " + id + " not found");
        }
    }

    private void applyServiceChargeUpdates(PutServiceChargeDTO putServiceChargeDTO, ServiceCharge serviceCharge) {
        putServiceChargeDTO.getTitle().ifPresent(serviceCharge::setTitle);
        putServiceChargeDTO.getServiceChargeValue().ifPresent(serviceCharge::setServiceChargeValue);
        putServiceChargeDTO.getValueType().ifPresent(serviceCharge::setValueType);
        putServiceChargeDTO.getCurrency().ifPresent(serviceCharge::setCurrency);
    }

    public ServiceChargeDTO getServiceChargeById(UUID id) {
        ServiceCharge serviceCharge = serviceChargeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ServiceCharge with id " + id + " not found"));
        return serviceChargeMapper.toServiceChargeDTO(serviceCharge);
    }
}

