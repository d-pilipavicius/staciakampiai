package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.api.DTOs.ServiceChargeDTOs.PostServiceChargeDTO;
import com.example.demo.orders.domain.entities.ServiceCharge;
import com.example.demo.orders.api.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects.FullServiceCharge;

import java.util.Optional;

public class ServiceChargeMapper {

    public static final StaticMapper<PostServiceChargeDTO, ServiceCharge> TO_MODEL = dto -> {
        ServiceCharge serviceCharge = new ServiceCharge();
        serviceCharge.setTitle(dto.getTitle());
        serviceCharge.setBusinessId(dto.getBusinessId());
        serviceCharge.setValue(dto.getValue());
        serviceCharge.setValueType(dto.getValueType());
        dto.getCurrency().ifPresent(serviceCharge::setCurrency);
        return serviceCharge;
    };

    public static final StaticMapper<ServiceCharge, FullServiceCharge> TO_DTO = entity -> {
        FullServiceCharge serviceChargeDTO = new FullServiceCharge();
        serviceChargeDTO.setId(entity.getId());
        serviceChargeDTO.setTitle(entity.getTitle());
        serviceChargeDTO.setBusinessId(entity.getBusinessId());
        serviceChargeDTO.setValue(entity.getValue());
        serviceChargeDTO.setValueType(entity.getValueType());
        serviceChargeDTO.setCurrency(Optional.ofNullable(entity.getCurrency()));
        return serviceChargeDTO;
    };
}
