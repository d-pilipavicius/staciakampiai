package com.example.demo.serviceChargeComponent.domain.services;

import com.example.demo.serviceChargeComponent.api.dtos.GetServiceChargesDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PostServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PutServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.serviceChargeComponent.domain.entities.ServiceCharge;
import com.example.demo.serviceChargeComponent.helper.mapper.ServiceChargeMapper;
import com.example.demo.serviceChargeComponent.repository.ServiceChargeRepository;

import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class ServiceChargeService {

    private final ServiceChargeRepository serviceChargeRepository;
    private final ServiceChargeMapper serviceChargeMapper;
    private static final Logger logger = LoggerFactory.getLogger(ServiceChargeService.class);


    public ServiceChargeDTO createServiceCharge(PostServiceChargeDTO postServiceChargeDTO) {
        ServiceCharge serviceCharge = serviceChargeMapper.toServiceCharge(postServiceChargeDTO);
        ServiceCharge savedServiceCharge = serviceChargeRepository.save(serviceCharge);
        
        ServiceChargeDTO savedServiceChargeDTO = serviceChargeMapper.toServiceChargeDTO(savedServiceCharge);
        logger.info("Created service charge with ID: {}, Details: {}", savedServiceChargeDTO.getId(), savedServiceChargeDTO.toString());

        return savedServiceChargeDTO;
    }

    public GetServiceChargesDTO getServiceCharges(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ServiceCharge> serviceChargePage = serviceChargeRepository.findAll(pageable);     
        return serviceChargeMapper.toGetServiceChargesDTO(serviceChargePage);
    }
    
    public ServiceChargeDTO updateServiceCharge(PutServiceChargeDTO putServiceChargeDTO, UUID id) {
        ServiceCharge serviceCharge = serviceChargeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceCharge with id " + id + " not found"));
        applyServiceChargeUpdates(putServiceChargeDTO, serviceCharge); 
        ServiceCharge updatedServiceCharge = serviceChargeRepository.save(serviceCharge);
        
        ServiceChargeDTO updatedServiceChargeDTO = serviceChargeMapper.toServiceChargeDTO(updatedServiceCharge);
        logger.info("Updated service charge with ID: {}, Updated details: {}", updatedServiceChargeDTO.getId(), updatedServiceChargeDTO);

        return updatedServiceChargeDTO;
    }


    public void deleteServiceCharge(UUID id) {
        if (serviceChargeRepository.existsById(id)) {
            serviceChargeRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("ServiceCharge with id " + id + " not found");
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
                .orElseThrow(() -> new EntityNotFoundException("ServiceCharge with id " + id + " not found"));
        return serviceChargeMapper.toServiceChargeDTO(serviceCharge);
    }
}

