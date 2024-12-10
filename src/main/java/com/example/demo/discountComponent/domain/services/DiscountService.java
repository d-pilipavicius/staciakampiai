package com.example.demo.discountComponent.domain.services;

import com.example.demo.discountComponent.api.dtos.DiscountDTO;
import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.discountComponent.api.dtos.PutDiscountDTO;
import com.example.demo.discountComponent.domain.entities.Discount;
import com.example.demo.discountComponent.helper.mapper.DiscountMapper;
import com.example.demo.helper.CustomExceptions.HTTPExceptions.HTTPExceptionJSON;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.repository.DiscountRepository;
import com.example.demo.discountComponent.validator.DiscountsValidator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountService.class);
    private final DiscountRepository discountRepository;
    private final DiscountsValidator discountsValidator;

    public DiscountDTO createDiscount(UUID employeeId, PostDiscountDTO postDiscountDTO){
        discountsValidator.checkIfAuthorized(employeeId);
        discountsValidator.checkDatesOverlap(postDiscountDTO.getValidFrom(), postDiscountDTO.getValidUntil());
        discountsValidator.checkPricingStrategy(postDiscountDTO.getCurrency(), postDiscountDTO.getValueType());
        discountsValidator.checkGiftcard(postDiscountDTO.getTarget(), postDiscountDTO.getUsageCountLimit(), postDiscountDTO.getCode());

        Discount createdDiscount = Mapper.mapToModel(postDiscountDTO, DiscountMapper.TO_DiscountModel);
        Discount savedDiscount = discountRepository.save(createdDiscount);

        return Mapper.mapToDTO(savedDiscount, DiscountMapper.TO_DiscountDTO);
    }

    public GetDiscountsDTO getDiscountsByBusinessId(UUID businessId, int page, int size){
        Page<DiscountDTO> discountDTOS;

        discountDTOS = discountRepository.findByBusinessId(businessId, PageRequest.of(page, size))
                .map(DiscountMapper.TO_DiscountDTO::map);

        discountsValidator.checkIfBusinessHadDiscounts((int) discountDTOS.getTotalElements());
        discountsValidator.checkIfDiscountPageExceeded(page, discountDTOS.getTotalPages());

        return Mapper.mapToDTO(discountDTOS, DiscountMapper.TO_GetDiscountsModel);
    }

    public DiscountDTO updateDiscount(UUID discountId, UUID employeeId, PutDiscountDTO putDiscountDTO){
        discountsValidator.checkIfAuthorized(employeeId);

        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> new HTTPExceptionJSON(
                HttpStatus.NOT_FOUND,
                "Invalid discount id",
                "The given discount id wasn't associated with any discount inside the database for updating."
            ));

        Discount updatedDiscount = createUpdatedDiscount(putDiscountDTO, discount);

        discountsValidator.checkDatesOverlap(updatedDiscount.getValidFrom(), updatedDiscount.getValidUntil());
        discountsValidator.checkPricingStrategy(updatedDiscount.getCurrency(), updatedDiscount.getValueType());
        discountsValidator.checkGiftcard(updatedDiscount.getTarget(), updatedDiscount.getUsageCountLimit(), updatedDiscount.getCode());

        Discount savedDiscount = discountRepository.save(discount);
        return Mapper.mapToDTO(savedDiscount, DiscountMapper.TO_DiscountDTO);
    }

    public void deleteDiscountById(UUID discountId, UUID employeeId){
        discountsValidator.checkIfAuthorized(employeeId);
        discountsValidator.checkIfDiscountExists(discountId);
        discountRepository.deleteById(discountId);
    }

    private Discount createUpdatedDiscount(PutDiscountDTO updatesToDiscount, Discount original){
        updatesToDiscount.getCode().ifPresentOrElse(original::setCode, () -> original.setCode(null));
        original.setProductIds(updatesToDiscount.getEntitledProductIds());
        original.setAmount(updatesToDiscount.getAmount());
        updatesToDiscount.getCurrency().ifPresentOrElse(original::setCurrency, () -> original.setCurrency(null));
        original.setValueType(updatesToDiscount.getValueType());
        original.setTarget(updatesToDiscount.getTarget());
        original.setValidFrom(updatesToDiscount.getValidFrom());
        original.setValidUntil(updatesToDiscount.getValidUntil());
        return original;
    }
}
