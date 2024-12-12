package com.example.demo.productComponent.domain.services;

import com.example.demo.CommonHelper.CustomExceptions.HTTPExceptions.HTTPExceptionJSON;
import com.example.demo.CommonHelper.mapper.base.Mapper;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.GetModifiersDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PutModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.domain.entities.ProductModifier;
import com.example.demo.productComponent.helper.mapper.ProductModifierMapper;
import com.example.demo.productComponent.helper.validator.ProductModifierValidator;
import com.example.demo.productComponent.repository.ProductCompatibleModifierRepository;
import com.example.demo.productComponent.repository.ProductModifierRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

        // Fetch modifiers
        List<ProductModifier> productModifiers = productModifierRepository.findAllById(modifierIds);

        // Map the modifiers to DTOs
        List<ProductModifierDTO> productModifierDTOS = Mapper.mapToDTOList(productModifiers,
                ProductModifierMapper.TO_DTO);

        // build and return the DTO
        return Mapper.mapToDTO(productModifierDTOS, ProductModifierMapper.LIST_TO_GET_MODIFIERS_DTO);
    }

    public GetModifiersDTO getModifiersByIds(List<UUID> modifierIds) {
        // Fetch modifiers
        List<ProductModifier> productModifiers = productModifierRepository.findAllById(modifierIds);

        // Map the modifiers to DTOs
        List<ProductModifierDTO> productModifierDTOS = Mapper.mapToDTOList(productModifiers,
                ProductModifierMapper.TO_DTO);

        return Mapper.mapToDTO(productModifierDTOS, ProductModifierMapper.LIST_TO_GET_MODIFIERS_DTO);
    }

    public GetModifiersDTO getModifiersByBusinessId(int page, int size, UUID businessId) {
        // Fetch modifiers
        Page<ProductModifier> productModifiers = productModifierRepository
                .findAllByBusinessId(PageRequest.of(page, size), businessId);

        // Map the modifiers to DTOs
        Page<ProductModifierDTO> productModifierDTOS = Mapper.mapToDTOPage(productModifiers,
                ProductModifierMapper.TO_DTO);

        return Mapper.mapToDTO(productModifierDTOS, ProductModifierMapper.PAGE_TO_GET_MODIFIERS_DTO);
    }

    @Transactional
    public ProductModifierDTO updateModifier(PutModifierDTO putModifierDTO, UUID id) {
        // Fetch the product modifier
        ProductModifier productModifier = productModifierRepository.findById(id)
                .orElseThrow(() -> new HTTPExceptionJSON(
                        HttpStatus.NOT_FOUND,
                        "Not found",
                        "Modifier with id " + id + " not found"));

        // Apply updates to the product modifier
        applyModifierUpdates(putModifierDTO, productModifier);

        // Save the updated product modifier
        ProductModifier updatedProductModifier = productModifierRepository.save(productModifier);

        // Map the updated product modifier to DTO and return it
        return Mapper.mapToDTO(updatedProductModifier, ProductModifierMapper.TO_DTO);
    }

    @Transactional
    public void deleteModifier(UUID modifierId) {
        // Validate that the modifier exists
        productModifierValidator.modifierExists(modifierId);
        // Delete the compatible modifiers
        productCompatibleModifierRepository.deleteByModifierId(modifierId);
        // Delete the modifier
        productModifierRepository.deleteById(modifierId);
    }

    private void applyModifierUpdates(PutModifierDTO putModifierDTO, ProductModifier modifier) {
        // Should be valid at this point
        modifier.setTitle(putModifierDTO.getTitle());
        modifier.setQuantityInStock(putModifierDTO.getQuantityInStock());
        modifier.setPrice(putModifierDTO.getPrice().getAmount());
        modifier.setCurrency(putModifierDTO.getPrice().getCurrency());
    }
}
