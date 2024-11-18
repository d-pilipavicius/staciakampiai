package com.example.demo.orders.API.DTOs.DiscountDTO;


import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountDTOsObjects.FullDiscount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPatchReturnDiscountDTO {
    private FullDiscount discount;
}
