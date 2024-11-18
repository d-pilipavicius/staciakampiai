package com.example.demo.orders.API.DTOs.DiscountDTO.DiscountDTOsObjects;

import com.example.demo.orders.API.DTOs.DiscountDTO.PostDiscountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountObject extends PostDiscountDTO {
    private UUID id;
    private int usageCount;
}
