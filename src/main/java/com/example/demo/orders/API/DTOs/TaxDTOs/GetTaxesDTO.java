package com.example.demo.orders.API.DTOs.TaxDTOs;

import com.example.demo.orders.API.DTOs.TaxDTOs.TaxHelperDTOs.TaxDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTaxesDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<TaxDTO> items;
}
