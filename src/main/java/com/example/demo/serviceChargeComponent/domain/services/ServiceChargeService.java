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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceChargeService {

    private final ServiceChargeRepository serviceChargeRepository;
    private final ServiceChargeMapper serviceChargeMapper;

    public ServiceChargeDTO createServiceCharge(PostServiceChargeDTO postServiceChargeDTO) {
        ServiceCharge serviceCharge = serviceChargeMapper.toServiceCharge(postServiceChargeDTO);
        ServiceCharge savedServiceCharge = serviceChargeRepository.save(serviceCharge);
        return serviceChargeMapper.toServiceChargeDTO(savedServiceCharge);
    }

    public GetServiceChargesDTO getServiceCharges(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ServiceCharge> serviceChargeEntities = serviceChargeRepository.findAll(pageable).getContent();

        List<ServiceChargeDTO> serviceChargeDTOs = serviceChargeEntities.stream()
                .map(serviceChargeMapper::toServiceChargeDTO)
                .collect(Collectors.toList());

        return new GetServiceChargesDTO(
                serviceChargeDTOs.size(),  
                size,                     
                page,                    
                serviceChargeDTOs         
        );
    }

    @Transactional
    public ServiceChargeDTO updateServiceCharge(PutServiceChargeDTO putServiceChargeDTO, UUID id) {
        ServiceCharge serviceCharge = serviceChargeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceCharge with id " + id + " not found"));
        applyServiceChargeUpdates(putServiceChargeDTO, serviceCharge); 
        ServiceCharge updatedServiceCharge = serviceChargeRepository.save(serviceCharge);
        
        return serviceChargeMapper.toServiceChargeDTO(updatedServiceCharge);
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
}

