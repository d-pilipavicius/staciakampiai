package com.example.demo.orders.API.DTOs.TaxDTOs;

import com.example.demo.orders.API.DTOs.TaxDTOs.TaxDTOsObjects.FullTax;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPatchReturnTaxDTO {
    private FullTax tax;
}
