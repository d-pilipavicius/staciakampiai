package com.example.demo.taxComponent.api.dtos;

import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
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
