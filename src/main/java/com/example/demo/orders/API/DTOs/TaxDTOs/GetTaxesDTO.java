package com.example.demo.orders.API.DTOs.TaxDTOs;

import com.example.demo.orders.API.DTOs.BaseDTOs.GetBaseDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.TaxDTOsObjects.TaxObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTaxesDTO extends GetBaseDTO {
    private List<TaxObject> items;
}
