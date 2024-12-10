package com.example.demo.taxComponent.api.dtos;

import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetTaxesDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<TaxDTO> items;

}
