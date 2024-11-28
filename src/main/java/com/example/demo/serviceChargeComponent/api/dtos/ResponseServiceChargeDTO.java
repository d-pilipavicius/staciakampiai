package com.example.demo.serviceChargeComponent.api.dtos;

import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * A response service charge DTO for Patch and Post requests
 */
public class ResponseServiceChargeDTO {
    private ServiceChargeDTO serviceCharge;
}
