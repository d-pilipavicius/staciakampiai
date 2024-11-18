package com.example.demo.orders.API.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects;

import com.example.demo.orders.API.DTOs.ServiceChargeDTOs.PostServiceChargeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceChargeObject extends PostServiceChargeDTO {
    private UUID id;
}
