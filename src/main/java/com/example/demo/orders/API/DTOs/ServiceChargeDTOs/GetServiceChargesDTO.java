package com.example.demo.orders.API.DTOs.ServiceChargeDTOs;

<<<<<<< HEAD
import com.example.demo.orders.API.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects.FullServiceCharge;
=======
import com.example.demo.orders.API.DTOs.BaseDTOs.GetBaseDTO;
import com.example.demo.orders.API.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects.ServiceChargeObject;
import lombok.AllArgsConstructor;
>>>>>>> 04c1e4641a5e9c3ebbe6c4e06a668b5a8f806baf
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceChargesDTO extends GetBaseDTO {
    private List<ServiceChargeObject> items;
}
