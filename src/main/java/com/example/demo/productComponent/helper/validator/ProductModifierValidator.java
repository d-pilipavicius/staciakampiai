package com.example.demo.productComponent.helper.validator;

import com.example.demo.productComponent.api.dtos.ModifierDTOs.PatchModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class ProductModifierValidator {

    private static final Logger logger = LoggerFactory.getLogger(ProductModifierValidator.class);

    public void validatePatchModifierDTO(PatchModifierDTO patchModifierDTO) {
        patchModifierDTO.getTitle().ifPresent(this::validateTitle);
        patchModifierDTO.getQuantityInStock().ifPresent(this::validateQuantityInStock);
        patchModifierDTO.getPrice().ifPresent(priceDTO -> validateAmount(priceDTO.getAmount()));
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
            logger.error("Amount cannot be negative");
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }
}
