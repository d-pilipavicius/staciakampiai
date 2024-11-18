package com.example.demo.orders.API.DTOs.DiscountDTO;

import com.example.demo.orders.API.DTOs.BaseDTOs.GetBaseDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountDTOsObjects.FullDiscount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDiscountsDTO extends GetBaseDTO {
    private List<FullDiscount> items;
}
