package com.example.demo.discountComponent.domain.services;

import com.example.demo.discountComponent.api.dtos.DiscountHelperDTOs.DiscountDTO;
import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.discountComponent.api.dtos.PatchDiscountDTO;
import com.example.demo.discountComponent.domain.entities.Discount;
import com.example.demo.discountComponent.helper.mapper.DiscountMapper;
import com.example.demo.helper.CustomExceptions.HTTPExceptions.HTTPExceptionJSON;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.api.dtos.ResponseDiscountDTO;
import com.example.demo.discountComponent.repository.AppliedDiscountRepository;
import com.example.demo.discountComponent.repository.DiscountRepository;
import com.example.demo.discountComponent.validator.DiscountsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountService.class);
    private final DiscountRepository discountRepository;
    private final AppliedDiscountRepository appliedDiscountRepository;
    private final DiscountsValidator discountsValidator;

    public DiscountService(DiscountRepository discountRepository, AppliedDiscountRepository appliedDiscountRepository,
                            DiscountsValidator discountsValidator) {
        this.discountRepository = discountRepository;
        this.appliedDiscountRepository = appliedDiscountRepository;
        this.discountsValidator = discountsValidator;
    }

    public ResponseDiscountDTO createDiscount(UUID employeeId, PostDiscountDTO postDiscountDTO){
        discountsValidator.checkIfAuthorized(employeeId);
        discountsValidator.checkDatesOverlap(postDiscountDTO.getValidFrom(), postDiscountDTO.getValidUntil());
        discountsValidator.checkPricingStrategy(postDiscountDTO.getCurrency(), postDiscountDTO.getValueType());
        discountsValidator.checkGiftcard(postDiscountDTO.getTarget(), postDiscountDTO.getUsageCountLimit(), postDiscountDTO.getCode());

        return ResponseDiscountDTO
                .builder()
                .discount(Mapper.mapToDTO(
                        discountRepository.save(
                                Mapper.mapToModel(
                                        postDiscountDTO,
                                        DiscountMapper.TO_MODEL
                                )
                        ),
                        DiscountMapper.TO_DTO
                ))
                .build();
    }

    public GetDiscountsDTO getDiscountsByBusinessId(UUID businessId, int page, int size){
        Page<DiscountDTO> discounts;

        discounts = discountRepository.findByBusinessId(businessId, PageRequest.of(page, size))
                .map(DiscountMapper.TO_DTO::map);

        discountsValidator.checkIfBusinessHadDiscounts((int) discounts.getTotalElements());
        discountsValidator.checkIfDiscountPageExceeded(page, discounts.getTotalPages());

        return GetDiscountsDTO
                .builder()
                .totalPages(discounts.getTotalPages())
                .totalItems((int) discounts.getTotalElements())
                .currentPage(page)
                .items(discounts.getContent())
                .build();
    }

    public ResponseDiscountDTO updateDiscount(UUID discountId, UUID employeeId, PatchDiscountDTO patchDiscountDTO){
        discountsValidator.checkIfAuthorized(employeeId);

        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> new HTTPExceptionJSON(
                HttpStatus.NOT_FOUND,
                "Invalid discount id",
                "The given discount id wasn't associated with any discount inside the database for updating."
            ));

        Discount updatedDiscount = createUpdatedDiscount(patchDiscountDTO, discount);

        discountsValidator.checkDatesOverlap(updatedDiscount.getValidFrom(), updatedDiscount.getValidUntil());
        discountsValidator.checkPricingStrategy(updatedDiscount.getCurrency(), updatedDiscount.getValueType());
        discountsValidator.checkGiftcard(updatedDiscount.getTarget(), updatedDiscount.getUsageCountLimit(), updatedDiscount.getCode());

        return ResponseDiscountDTO
                .builder()
                .discount(Mapper.mapToDTO(
                        discountRepository.save(discount),
                        DiscountMapper.TO_DTO
                ))
                .build();
    }

    public void deleteDiscountById(UUID discountId, UUID employeeId){
        discountsValidator.checkIfAuthorized(employeeId);
        discountsValidator.checkIfDiscountExists(discountId);
        discountRepository.deleteById(discountId);
    }

    private Discount createUpdatedDiscount(PatchDiscountDTO updatesToDiscount, Discount original){
        original.setCode(updatesToDiscount.getCode() == null ?
                original.getCode() : updatesToDiscount.getCode().orElse(null));
        updatesToDiscount.getEntitledProductIds().ifPresent(original::setProductIds);
        updatesToDiscount.getAmount().ifPresent(original::setAmount);
        updatesToDiscount.getValueType().ifPresent(original::setValueType);
        original.setCurrency(updatesToDiscount.getCurrency() == null ?
                original.getCurrency() : updatesToDiscount.getCurrency().orElse(null));
        updatesToDiscount.getTarget().ifPresent(original::setTarget);
        updatesToDiscount.getValidFrom().ifPresent(original::setValidFrom);
        updatesToDiscount.getValidUntil().ifPresent(original::setValidUntil);
        return original;
    }
}
