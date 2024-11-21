package com.example.demo.orders.domain.services;

import com.example.demo.helper.mapper.DiscountMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountHelperDTOs.DiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.GetDiscountsDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PatchDiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PostDiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.ResponseDiscountDTO;
import com.example.demo.orders.domain.entities.Discount;
import com.example.demo.orders.repository.DiscountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountService.class);

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
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

        // TODO: add constructor to GetDiscountsDTO
        GetDiscountsDTO getDiscountsDTO = new GetDiscountsDTO();
        getDiscountsDTO.setItems(discounts);
        return getDiscountsDTO;
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

    @Transactional
    public ResponseDiscountDTO updateDiscount(PatchDiscountDTO patchDiscountDTO, UUID id){
        Optional<Discount> discount = discountRepository.findById(id);

        if(discount.isEmpty()){
            throw new IllegalArgumentException("Discount not found");
        }

        // TODO: abstract this to a new method or smth mb?
        patchDiscountDTO.getCode().ifPresent(discount.get()::setCode);
        patchDiscountDTO.getEntitledProductIds().ifPresent(discount.get()::setProductIds);
        patchDiscountDTO.getValue().ifPresent(discount.get()::setValue);
        patchDiscountDTO.getValueType().ifPresent(discount.get()::setValueType);
        patchDiscountDTO.getCurrency().ifPresent(discount.get()::setCurrency);
        patchDiscountDTO.getTarget().ifPresent(discount.get()::setTarget);
        patchDiscountDTO.getValidFrom().ifPresent(discount.get()::setValidFrom);
        patchDiscountDTO.getValidUntil().ifPresent(discount.get()::setValidUntil);

        ResponseDiscountDTO newDiscountDTO = new ResponseDiscountDTO();
        newDiscountDTO.setDiscount(Mapper.mapToDTO(
                discountRepository.save(discount.get()),
                DiscountMapper.TO_DTO
        ));

        return newDiscountDTO;
    }

    public void deleteDiscount(UUID id){
        discountRepository.deleteById(id);
    }
}
