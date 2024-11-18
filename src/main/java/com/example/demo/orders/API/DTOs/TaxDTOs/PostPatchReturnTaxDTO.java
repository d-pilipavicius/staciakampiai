package com.example.demo.orders.API.DTOs.TaxDTOs;

import com.example.demo.orders.API.DTOs.TaxDTOs.TaxDTOsObjects.FullTax;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPatchReturnTaxDTO {
    private FullTax tax;
}
