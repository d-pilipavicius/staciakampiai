package com.example.demo.orders.api.DTOs.ServiceChargeDTOs;

import com.example.demo.orders.api.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects.FullServiceCharge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPatchReturnServiceChargeDTO {
    private FullServiceCharge serviceCharge;
}
