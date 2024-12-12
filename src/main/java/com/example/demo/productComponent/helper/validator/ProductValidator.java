package com.example.demo.productComponent.helper.validator;

import com.example.demo.CommonHelper.CustomExceptions.HTTPExceptions.HTTPExceptionJSON;
import com.example.demo.productComponent.repository.ProductCompatibleModifierRepository;
import com.example.demo.productComponent.repository.ProductModifierRepository;
import com.example.demo.productComponent.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "Product with id " + productId + " does not exist");
        }
    }

    public void modifierExists(UUID modifierId) {
        if (!productModifierRepository.existsById(modifierId)) {
            logger.error("Modifier with id {} does not exist", modifierId);
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "Modifier with id " + modifierId + " does not exist");
        }
    }

    public boolean compatibleModifierExists(UUID productId, UUID modifierIds) {
        return productCompatibleModifierRepository.existsByProductIdAndModifierId(productId, modifierIds);
    }
}
