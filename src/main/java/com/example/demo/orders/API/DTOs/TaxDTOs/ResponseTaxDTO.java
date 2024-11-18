package com.example.demo.orders.API.DTOs.TaxDTOs;

import com.example.demo.orders.API.DTOs.TaxDTOs.TaxHelperDTOs.TaxDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * A response tax DTO for Patch and Post requests
 */
public class ResponseTaxDTO {
    private TaxDTO tax;
}
