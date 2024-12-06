package com.example.demo.taxComponent.api.dtos;

import com.example.demo.taxComponent.api.dtos.TaxHelperDTOs.TaxDTO;
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

    public GetTaxesDTO(List<TaxDTO> taxes, int totalItems) {
        this.items = taxes;
        this.totalItems = totalItems;
    }
}
