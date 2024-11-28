package com.example.demo.discountComponent.applicationServices;

import com.example.demo.discountComponent.api.dtos.DiscountHelperDTOs.DiscountDTO;
import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.discountComponent.api.dtos.PatchDiscountDTO;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.api.dtos.ResponseDiscountDTO;
import com.example.demo.discountComponent.domain.services.DiscountService;
import com.example.demo.productComponent.applicationServices.ProductApplicationService;
import com.example.demo.productComponent.domain.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DiscountApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountApplicationService.class);

    private final DiscountService discountService;
    private final ProductApplicationService productApplicationService;

    public DiscountApplicationService(
            DiscountService discountService,
            ProductApplicationService productApplicationService
    ) {
        this.discountService = discountService;
        this.productApplicationService = productApplicationService;
    }

    @Transactional
    public DiscountDTO createDiscount(PostDiscountDTO postDiscountDTO){
        postDiscountDTO.getEntitledProductIds().ifPresent(productIds -> {
            if (!productApplicationService.validateProductIds(productIds)) {
                throw new IllegalArgumentException("Invalid product IDs provided");
            }
        });

        return discountService.createDiscount(postDiscountDTO);
    }

    public GetDiscountsDTO getDiscountsByBusinessId(UUID businessId){
        return discountService.getDiscountsByBusinessId(businessId);
    }

    public GetDiscountsDTO getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    public GetDiscountsDTO getAllDiscounts(int page, int size) {
        return discountService.getAllDiscounts(page, size);
    }

    @Transactional
    public ResponseDiscountDTO updateDiscount(UUID discountId, PatchDiscountDTO patchDiscountDTO) {
        patchDiscountDTO.getEntitledProductIds().ifPresent(productIds -> {
            if (!productApplicationService.validateProductIds(productIds)) {
                throw new IllegalArgumentException("Invalid product IDs provided");
            }
        });

        return discountService.updateDiscount(patchDiscountDTO, discountId);
    }

    @Transactional
    public boolean deleteDiscount(UUID discountId) {
        discountService.deleteDiscount(discountId);
        return true;
    }
}
