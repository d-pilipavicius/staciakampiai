package com.example.demo.orders.API.DTOs.DiscountDTO;


import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountDTOsObjects.FullDiscount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPatchReturnDiscountDTO {
    private FullDiscount discount;
}
