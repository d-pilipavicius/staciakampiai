package com.example.demo.serviceChargeComponent.api.dtos;

import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceChargesDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<ServiceChargeDTO> items;

    public GetServiceChargesDTO(List<ServiceChargeDTO> serviceCharges, int size) {
        this.items = serviceCharges;
        this.totalItems = size;
    }
}
