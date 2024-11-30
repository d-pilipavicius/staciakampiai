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
import org.springframework.dao.EmptyResultDataAccessException;
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

    public DiscountService(DiscountRepository discountRepository, AppliedDiscountRepository appliedDiscountRepository) {
        this.discountRepository = discountRepository;
        this.appliedDiscountRepository = appliedDiscountRepository;
    }

    public ResponseDiscountDTO createDiscount(UUID employeeId, PostDiscountDTO postDiscountDTO){
        DiscountsValidator.checkIfAuthorized(employeeId);
        DiscountsValidator.checkDatesOverlap(postDiscountDTO.getValidFrom(), postDiscountDTO.getValidUntil());
        DiscountsValidator.checkPricingStrategy(postDiscountDTO.getCurrency(), postDiscountDTO.getValueType());
        DiscountsValidator.checkGiftcard(postDiscountDTO.getTarget(), postDiscountDTO.getUsageCountLimit(), postDiscountDTO.getCode());

        return new ResponseDiscountDTO(Mapper.mapToDTO(
                discountRepository.save(
                        Mapper.mapToModel(
                                postDiscountDTO,
                                DiscountMapper.TO_MODEL
                        )
                ),
                DiscountMapper.TO_DTO
        ));
    }

    public GetDiscountsDTO getDiscountsByBusinessId(UUID businessId, int page, int size){
        Page<DiscountDTO> discounts;
        discounts = discountRepository.findByBusinessId(businessId, PageRequest.of(page, size))
                .map(DiscountMapper.TO_DTO::map);

        DiscountsValidator.checkIfBusinessHadDiscounts((int) discounts.getTotalElements());

        return GetDiscountsDTO
                .builder()
                .totalPages(discounts.getTotalPages())
                .totalItems((int) discounts.getTotalElements())
                .currentPage(page)
                .items(discounts.getContent())
                .build();
    }

    public ResponseDiscountDTO updateDiscount(UUID discountId, UUID employeeId, PatchDiscountDTO patchDiscountDTO){
        DiscountsValidator.checkIfAuthorized(employeeId);

        Discount discount = discountRepository.findById(discountId).orElseGet(() -> {
            throw new HTTPExceptionJSON(
                    HttpStatus.NOT_FOUND,
                    "No data was found by Id",
                    "The provided discount Id, which was used to update an existing discount, wasn't associated with any discount in the database."
            );
        });

        Discount updatedDiscount = createUpdatedDiscount(patchDiscountDTO, discount);

        DiscountsValidator.checkDatesOverlap(updatedDiscount.getValidFrom(), updatedDiscount.getValidUntil());
        DiscountsValidator.checkPricingStrategy(updatedDiscount.getCurrency(), updatedDiscount.getValueType());
        DiscountsValidator.checkGiftcard(updatedDiscount.getTarget(), updatedDiscount.getUsageCountLimit(), updatedDiscount.getCode());

        return ResponseDiscountDTO
                .builder()
                .discount(Mapper.mapToDTO(
                        discountRepository.save(discount),
                        DiscountMapper.TO_DTO
                ))
                .build();
    }

    public void deleteDiscountbyId(UUID discountId, UUID employeeId){
        DiscountsValidator.checkIfAuthorized(employeeId);
        try{
            discountRepository.deleteById(discountId);
        } catch (EmptyResultDataAccessException e){
            throw new HTTPExceptionJSON(
                    HttpStatus.NOT_FOUND,
                    "No data was found by Id",
                    "The specified discount Id, was not found in the database for deletion."
            );
        }
    }

    private Discount createUpdatedDiscount(PatchDiscountDTO updatesToDiscount, Discount original){
        updatesToDiscount.getCode().ifPresent(original::setCode);
        updatesToDiscount.getEntitledProductIds().ifPresent(original::setProductIds);
        updatesToDiscount.getAmount().ifPresent(original::setAmount);
        updatesToDiscount.getValueType().ifPresent(original::setValueType);
        updatesToDiscount.getCurrency().ifPresent(original::setCurrency);
        updatesToDiscount.getTarget().ifPresent(original::setTarget);
        updatesToDiscount.getValidFrom().ifPresent(original::setValidFrom);
        updatesToDiscount.getValidUntil().ifPresent(original::setValidUntil);
        return original;
    }
}
