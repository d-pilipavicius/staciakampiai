package com.example.demo.orders.services;

import com.example.demo.orders.API.DTOs.DiscountDTO.GetDiscountsDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PatchDiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.ResponseDiscountDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DiscountApplicationService {
    public ResponseDiscountDTO createAndReturnDiscount(){
        return new ResponseDiscountDTO();
    }

    public GetDiscountsDTO returnAllDiscountsByBusinessId(UUID businessId){
        return new GetDiscountsDTO();
    }

    public ResponseDiscountDTO updateAndReturnDiscount(UUID discountId, PatchDiscountDTO discount){return new ResponseDiscountDTO();}

    public boolean deleteDiscountById(UUID disocuntId){return true;}
}
