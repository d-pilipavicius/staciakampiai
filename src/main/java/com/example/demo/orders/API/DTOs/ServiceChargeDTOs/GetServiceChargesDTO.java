package com.example.demo.orders.API.DTOs.ServiceChargeDTOs;

import com.example.demo.orders.API.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects.FullServiceCharge;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetServiceChargesDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<FullServiceCharge> items;
}
