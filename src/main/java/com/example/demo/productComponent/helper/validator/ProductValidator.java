package com.example.demo.productComponent.helper.validator;

import com.example.demo.productComponent.api.dtos.PatchProductDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.repository.ProductCompatibleModifierRepository;
import com.example.demo.productComponent.repository.ProductModifierRepository;
import com.example.demo.productComponent.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductValidator {

    private static final Logger logger = LoggerFactory.getLogger(ProductValidator.class);

    private final ProductRepository productRepository;
    private final ProductModifierRepository productModifierRepository;
    private final ProductCompatibleModifierRepository productCompatibleModifierRepository;

     public boolean productsExist(List<UUID> productIds) {
         long count = productRepository.countByIdIn(productIds);
         return count == productIds.size();
     }

     public boolean modifiersExist(List<UUID> modifierIds) {
         long count = productModifierRepository.countByIdIn(modifierIds);
         return count == modifierIds.size();
     }

    public void productExists(UUID productId) {
        if (!productRepository.existsById(productId)) {
            logger.error("Product with id {} does not exist", productId);
            throw new IllegalArgumentException("Product with id " + productId + " does not exist");
        }
    }

    public void modifierExists(UUID modifierId) {
        if (!productModifierRepository.existsById(modifierId)) {
            logger.error("Modifier with id {} does not exist", modifierId);
            throw new IllegalArgumentException("Modifier with id " + modifierId + " does not exist");
        }
    }

    public boolean compatibleModifierExists(UUID productId, UUID modifierIds) {
        return productCompatibleModifierRepository.existsByProductIdAndModifierId(productId, modifierIds);
    }

    // Validate the patch product DTO -> if fields are present, they should not be empty
    public void validatePatchProductDTO(PatchProductDTO patchProductDTO) {
        patchProductDTO.getTitle().ifPresent(this::validateTitle);
        patchProductDTO.getQuantityInStock().ifPresent(this::validateQuantityInStock);
        patchProductDTO.getPrice().ifPresent(priceDTO -> validateAmount(priceDTO.getAmount()));
        patchProductDTO.getCompatibleModifierIds().ifPresent(this::validateModifiers);
    }

    private void validateTitle(String title) {
        if (title.isBlank()) {
            logger.error("Title cannot be empty");
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }

    private void validateQuantityInStock(int quantityInStock) {
        if (quantityInStock < 0) {
            logger.error("Quantity in stock cannot be negative");
            throw new IllegalArgumentException("Quantity in stock cannot be negative");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            logger.error("Price cannot be negative");
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public void validateModifiers(List<UUID> modifierIds) {
        if (modifierIds.isEmpty()) {
            logger.error("Empty modifier ids");
            throw new IllegalArgumentException("Empty modifier ids");
        }
    }
}
