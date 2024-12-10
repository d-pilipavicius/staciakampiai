package com.example.demo.discountComponent.applicationServices;

import com.example.demo.discountComponent.api.dtos.DiscountDTO;
import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.discountComponent.api.dtos.PutDiscountDTO;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.domain.services.DiscountService;
//import com.example.demo.productComponent.applicationServices.ProductApplicationService;
//import com.example.demo.productComponent.domain.services.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DiscountApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountApplicationService.class);

    private final DiscountService discountService;
    //private final ProductApplicationService productApplicationService;

    public DiscountDTO createDiscount(UUID employeeId, PostDiscountDTO postDiscountDTO){
        //Need products component, so will get implemented later on
       /* postDiscountDTO.getEntitledProductIds().ifPresent(productIds -> {
            if (!productApplicationService.validateProductIds(productIds)) {
                throw new IllegalArgumentException("Invalid product IDs provided");
            }
        });*/

        return discountService.createDiscount(employeeId, postDiscountDTO);
    }

    public GetDiscountsDTO getDiscountsByBusinessId(UUID businessId, int page, int size){
        return discountService.getDiscountsByBusinessId(businessId, page, size);
    }

    public DiscountDTO updateDiscount(UUID discountId, UUID employeeId, PutDiscountDTO putDiscountDTO) {
        //Need products component, so will get implemented later on
        /*patchDiscountDTO.getEntitledProductIds().ifPresent(productIds -> {
            if (!productApplicationService.validateProductIds(productIds)) {
                throw new IllegalArgumentException("Invalid product IDs provided");
            }
        });*/

        return discountService.updateDiscount(discountId, employeeId, putDiscountDTO);
    }

    public boolean deleteDiscountById(UUID discountId, UUID employeeId) {
        discountService.deleteDiscountById(discountId, employeeId);
        return true;
    }
}
