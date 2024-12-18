package com.example.demo.discountComponent.applicationServices;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
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
    public DiscountDTO createDiscount(PostDiscountDTO postDiscountDTO){
        if(postDiscountDTO.getEntitledProductIds() != null && !productApplicationService.validateProductIds(postDiscountDTO.getEntitledProductIds())){
            throw new NotFoundException("The provided product ids were not found.");
        }
        return discountService.createDiscount(postDiscountDTO);
    }

    @Transactional
    public GetDiscountsDTO getDiscountsByBusinessId(UUID businessId, int page, int size, boolean giftcards){
        return discountService.getDiscountsByBusinessId(businessId, page, size, giftcards);
    }

    @Transactional
    public DiscountDTO updateUsage(UUID discountId){
        return discountService.updateUsage(discountId);
    }

    @Transactional
    public DiscountDTO updateDiscount(UUID discountId, PutDiscountDTO putDiscountDTO) {
        if(putDiscountDTO.getEntitledProductIds() != null){
            productApplicationService.validateProductIds(putDiscountDTO.getEntitledProductIds());
        }
        return discountService.updateDiscount(discountId, putDiscountDTO);
    }

    @Transactional
    public boolean deleteDiscountById(UUID discountId) {
        discountService.deleteDiscountById(discountId);
        return true;
    }
}
