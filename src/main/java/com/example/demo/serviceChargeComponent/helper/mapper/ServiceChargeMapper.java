package com.example.demo.serviceChargeComponent.helper.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.serviceChargeComponent.api.dtos.PostServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.serviceChargeComponent.domain.entities.ServiceCharge;

import java.util.Optional;

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
                .currency(postServiceChargeDTO.getCurrency().orElse(null))
                .build();
    }
}

