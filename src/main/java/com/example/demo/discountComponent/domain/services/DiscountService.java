package com.example.demo.discountComponent.domain.services;

import com.example.demo.discountComponent.helper.mapper.DiscountMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.discountComponent.api.dtos.DiscountHelperDTOs.DiscountDTO;
import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.discountComponent.api.dtos.PatchDiscountDTO;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.api.dtos.ResponseDiscountDTO;
import com.example.demo.discountComponent.domain.entities.Discount;
import com.example.demo.discountComponent.repository.AppliedDiscountRepository;
import com.example.demo.discountComponent.repository.DiscountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
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

    public DiscountDTO createDiscount(PostDiscountDTO postDiscountDTO){
        return Mapper.mapToDTO(
                discountRepository.save(
                        Mapper.mapToModel(
                                postDiscountDTO,
                                DiscountMapper.TO_MODEL
                        )
                ),
                DiscountMapper.TO_DTO
        );
    }

    public GetDiscountsDTO getAllDiscounts(){
        List<DiscountDTO> discounts = discountRepository.findAll().stream()
                .map(DiscountMapper.TO_DTO::map)
                .toList();

        return new GetDiscountsDTO(discounts);
    }

    public GetDiscountsDTO getAllDiscounts(int page, int size){
        List<DiscountDTO> discounts = discountRepository.findAll(PageRequest.of(page, size)).stream()
                .map(DiscountMapper.TO_DTO::map)
                .toList();

        return new GetDiscountsDTO(
                discounts.size(),
                size,
                page,
                discounts
        );
    }

    public GetDiscountsDTO getDiscountsByBusinessId(UUID businessId) {
        List<DiscountDTO> discounts = discountRepository.findAllById(Collections.singleton(businessId)).stream()
                .map(DiscountMapper.TO_DTO::map)
                .toList();

        return new GetDiscountsDTO(discounts);
    }

    @Transactional
    public ResponseDiscountDTO updateDiscount(PatchDiscountDTO patchDiscountDTO, UUID id){
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Discount not found"));

        // TODO: abstract this to a new method or smth mb?
        patchDiscountDTO.getCode().ifPresent(discount::setCode);
        patchDiscountDTO.getEntitledProductIds().ifPresent(discount::setProductIds);
        patchDiscountDTO.getValue().ifPresent(discount::setValue);
        patchDiscountDTO.getValueType().ifPresent(discount::setValueType);
        patchDiscountDTO.getCurrency().ifPresent(discount::setCurrency);
        patchDiscountDTO.getTarget().ifPresent(discount::setTarget);
        patchDiscountDTO.getValidFrom().ifPresent(discount::setValidFrom);
        patchDiscountDTO.getValidUntil().ifPresent(discount::setValidUntil);

        return new ResponseDiscountDTO(
                Mapper.mapToDTO(
                        discountRepository.save(discount),
                        DiscountMapper.TO_DTO
                )
        );
    }

    @Transactional
    public void deleteDiscount(UUID id){
        discountRepository.deleteById(id);
    }
}
