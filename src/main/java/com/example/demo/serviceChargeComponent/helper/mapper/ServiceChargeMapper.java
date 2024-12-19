package com.example.demo.serviceChargeComponent.helper.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.demo.serviceChargeComponent.api.dtos.GetServiceChargesDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PostServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.serviceChargeComponent.domain.entities.ServiceCharge;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ServiceChargeMapper {

    public ServiceChargeDTO toServiceChargeDTO(ServiceCharge serviceCharge) {
        return ServiceChargeDTO.builder()
                .id(serviceCharge.getId())
                .title(serviceCharge.getTitle())
                .businessId(serviceCharge.getBusinessId())
                .serviceChargeValue(serviceCharge.getServiceChargeValue())
                .valueType(serviceCharge.getValueType())
                .currency(Optional.ofNullable(serviceCharge.getCurrency()))
                .build();
    }

    public ServiceCharge toServiceCharge(PostServiceChargeDTO postServiceChargeDTO) {
        return ServiceCharge.builder()
                .title(postServiceChargeDTO.getTitle())
                .businessId(postServiceChargeDTO.getBusinessId())
                .serviceChargeValue(postServiceChargeDTO.getServiceChargeValue())
                .valueType(postServiceChargeDTO.getValueType())
                .currency(postServiceChargeDTO.getCurrency() == null ? null : postServiceChargeDTO.getCurrency())
                .build();
    }

    public GetServiceChargesDTO toGetServiceChargesDTO(Page<ServiceCharge> serviceChargePage) {
        List<ServiceChargeDTO> serviceChargeDTOs = serviceChargePage.getContent().stream()
                .map(this::toServiceChargeDTO)  
                .collect(Collectors.toList());

        return GetServiceChargesDTO.builder()
                .currentPage(serviceChargePage.getNumber())
                .totalItems((int) serviceChargePage.getTotalElements())  
                .totalPages(serviceChargePage.getTotalPages())
                .items(serviceChargeDTOs)
                .build();
    }

    
}

