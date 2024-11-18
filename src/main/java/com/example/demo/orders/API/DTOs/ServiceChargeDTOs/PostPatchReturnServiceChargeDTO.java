package com.example.demo.orders.API.DTOs.ServiceChargeDTOs;

import com.example.demo.orders.API.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects.FullServiceCharge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPatchReturnServiceChargeDTO {
    private FullServiceCharge serviceCharge;
}
