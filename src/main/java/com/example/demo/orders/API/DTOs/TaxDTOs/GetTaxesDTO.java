package com.example.demo.orders.API.DTOs.TaxDTOs;

import com.example.demo.orders.API.DTOs.TaxDTOs.TaxDTOsObjects.FullTax;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetTaxesDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<FullTax> items;
}
