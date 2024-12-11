package com.example.demo.discountComponent.applicationServices;

import com.example.demo.discountComponent.api.dtos.DiscountDTO;
import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.discountComponent.api.dtos.PutDiscountDTO;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.domain.services.DiscountService;
import com.example.demo.productComponent.applicationServices.ProductApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DiscountApplicationService {

    private final DiscountService discountService;
    private final ProductApplicationService productApplicationService;

    @Transactional
    public DiscountDTO createDiscount(UUID employeeId, PostDiscountDTO postDiscountDTO){
        if(postDiscountDTO.getEntitledProductIds() != null){
            productApplicationService.validateProductIds(postDiscountDTO.getEntitledProductIds());
        }
        return discountService.createDiscount(employeeId, postDiscountDTO);
    }

    @Transactional
    public GetDiscountsDTO getDiscountsByBusinessId(UUID businessId, int page, int size){
        return discountService.getDiscountsByBusinessId(businessId, page, size);
    }

    @Transactional
    public DiscountDTO updateDiscount(UUID discountId, UUID employeeId, PutDiscountDTO putDiscountDTO) {
        if(putDiscountDTO.getEntitledProductIds() != null){
            productApplicationService.validateProductIds(putDiscountDTO.getEntitledProductIds());
        }
        return discountService.updateDiscount(discountId, employeeId, putDiscountDTO);
    }

    @Transactional
    public boolean deleteDiscountById(UUID discountId, UUID employeeId) {
        discountService.deleteDiscountById(discountId, employeeId);
        return true;
    }
}
