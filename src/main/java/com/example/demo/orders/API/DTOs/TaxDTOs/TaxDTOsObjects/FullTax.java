package com.example.demo.orders.API.DTOs.TaxDTOs.TaxDTOsObjects;

import com.example.demo.orders.API.DTOs.TaxDTOs.PostTaxDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullTax extends PostTaxDTO {
    private UUID id;
}
