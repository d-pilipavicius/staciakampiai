package com.example.demo.orders.services;

import com.example.demo.orders.API.DTOs.DiscountDTO.GetDiscountsDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PatchDiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PostPatchReturnDiscountDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscountApplicationService {
    public PostPatchReturnDiscountDTO createAndReturnDiscount(){
        return null;
    }

    public GetDiscountsDTO returnAllDiscountsByBusinessId(UUID businessId){
        return new GetDiscountsDTO();
    }

    public PostPatchReturnDiscountDTO updateAndReturnDiscount(UUID discountId, PatchDiscountDTO discount){return new PostPatchReturnDiscountDTO();}

    public boolean deleteDiscountById(UUID disocuntId){return true;}
}
