package com.example.demo.productComponent.domain.services;

import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.GetModifiersDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PatchModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.ResponseModifierDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.domain.entities.ProductModifier;
import com.example.demo.productComponent.helper.mapper.ProductModifierMapper;
import com.example.demo.productComponent.helper.validator.ProductModifierValidator;
import com.example.demo.productComponent.repository.ProductCompatibleModifierRepository;
import com.example.demo.productComponent.repository.ProductModifierRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductModifierService {

    private final ProductModifierRepository productModifierRepository;
    private final ProductCompatibleModifierRepository productCompatibleModifierRepository;
    private final ProductModifierValidator productModifierValidator;

    @Transactional
    public ProductModifierDTO createModifier(PostModifierDTO postModifierDTO) {
        // Validate the DTO
        productModifierValidator.validatePostModifierDTO(postModifierDTO);
        // map the DTO to the model
        ProductModifier modifier = Mapper.mapToModel(postModifierDTO, ProductModifierMapper.TO_MODEL);
        // save the modifier
        ProductModifier savedModifier = productModifierRepository.save(modifier);
        // map the saved modifier to a DTO and return it
        return Mapper.mapToDTO(savedModifier, ProductModifierMapper.TO_DTO);
    }

    // Fetches all modifiers that are linked to the product
    public GetModifiersDTO getModifiersByProductId(UUID productId) {
        // Fetch modifier Ids
        List<UUID> modifierIds = productCompatibleModifierRepository.findModifierIdsByProductId(productId);
        // Fetch modifiers and map to DTOs
        List<ProductModifierDTO> productModifierDTOS = productModifierRepository.findAllById(modifierIds).stream()
                .map(ProductModifierMapper.TO_DTO::map)
                .toList();

        // build and return the DTO
        return GetModifiersDTO.builder().items(productModifierDTOS).build();
    }

    public GetModifiersDTO getModifiersByIds(List<UUID> modifierIds) {
        // Fetch modifiers and map to DTOs
        List<ProductModifierDTO> productModifierDTOS = productModifierRepository.findAllById(modifierIds).stream()
                .map(ProductModifierMapper.TO_DTO::map)
                .toList();

        return GetModifiersDTO.builder()
                .items(productModifierDTOS)
                .totalItems(productModifierDTOS.size())
                .build();
    }

    public GetModifiersDTO getModifiersByBusinessId(int page, int size, UUID businessId) {
        // Fetch modifiers and map to DTOs
        Page<ProductModifierDTO> productModifierDTOS = productModifierRepository.findAllByBusinessId(PageRequest.of(page, size), businessId)
                .map(ProductModifierMapper.TO_DTO::map);

        return GetModifiersDTO.builder()
                .items(productModifierDTOS.getContent())
                .totalItems((int)productModifierDTOS.getTotalElements())
                .currentPage(page)
                .totalPages(productModifierDTOS.getTotalPages())
                .build();
    }

    @Transactional
    public ResponseModifierDTO updateModifier(PatchModifierDTO patchModifierDTO, UUID id){

        // Validate the DTO
        productModifierValidator.validatePatchModifierDTO(patchModifierDTO);

        // Fetch the product modifier
        ProductModifier productModifier = productModifierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product modifier not found"));

        // Apply updates to the product modifier
        applyModifierUpdates(patchModifierDTO, productModifier);

        // Save the updated product modifier
        ProductModifier updatedProductModifier = productModifierRepository.save(productModifier);

        // Map the updated product modifier to DTO and return it
        return ResponseModifierDTO.builder()
                .productModifier(Mapper.mapToDTO(updatedProductModifier, ProductModifierMapper.TO_DTO))
                .build();
    }

    @Transactional
    public void deleteModifier(UUID modifierId) {
        productCompatibleModifierRepository.deleteByModifierId(modifierId);
        productModifierRepository.deleteById(modifierId);
    }

    private void applyModifierUpdates(PatchModifierDTO patchModifierDTO, ProductModifier modifier) {
        patchModifierDTO.getTitle().ifPresent(modifier::setTitle);
        patchModifierDTO.getQuantityInStock().ifPresent(modifier::setQuantityInStock);
        patchModifierDTO.getPrice().ifPresent(moneyDTO -> {
            modifier.setPrice(moneyDTO.getAmount());
            modifier.setCurrency(moneyDTO.getCurrency());
        });
    }
}
