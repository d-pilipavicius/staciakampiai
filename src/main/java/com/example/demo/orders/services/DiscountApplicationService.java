package com.example.demo.orders.services;

import com.example.demo.helper.mapper.DiscountMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountHelperDTOs.DiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.GetDiscountsDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PatchDiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PostDiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.ResponseDiscountDTO;
import com.example.demo.orders.domain.entities.Discount;
import com.example.demo.orders.domain.services.DiscountService;
import com.example.demo.orders.domain.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DiscountApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountApplicationService.class);

    private final DiscountService discountService;
    private final ProductService productService;

    public DiscountApplicationService(
            DiscountService discountService,
            ProductService productService
    ) {
        this.discountService = discountService;
        this.productService = productService;
    }

    @Transactional
    public DiscountDTO createDiscount(PostDiscountDTO postDiscountDTO){
        if(postDiscountDTO.getEntitledProductIds().isPresent()){
            List<UUID> productIds = postDiscountDTO.getEntitledProductIds().get();
            if(!productService.validateProductIds(productIds)){
                throw new IllegalArgumentException("Product ids are not valid");
            }
        }

        return discountService.createDiscount(postDiscountDTO);
    }

    public GetDiscountsDTO returnAllDiscountsByBusinessId(UUID businessId){
        return new GetDiscountsDTO();
    }

    public ResponseDiscountDTO updateAndReturnDiscount(UUID discountId, PatchDiscountDTO discount){return new ResponseDiscountDTO();}

    public boolean deleteDiscountById(UUID disocuntId){return true;}
}
