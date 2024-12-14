package com.example.demo.productComponent.helper.validator;

import com.example.demo.helper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.helper.ErrorHandling.CustomExceptions.UnprocessableException;
import com.example.demo.productComponent.repository.ProductCompatibleModifierRepository;
import com.example.demo.productComponent.repository.ProductModifierRepository;
import com.example.demo.productComponent.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

     public void productsExist(List<UUID> productIds) {
         long count = productRepository.countByIdIn(productIds);
         if(count != productIds.size()){
             throw new NotFoundException(
                     "Not all given product ids exist."
             );
         };
     }

     public boolean modifiersExist(List<UUID> modifierIds) {
         long count = productModifierRepository.countByIdIn(modifierIds);
         return count == modifierIds.size();
     }

    public void productExists(UUID productId) {
        if (!productRepository.existsById(productId)) {
            logger.error("Product with id {} does not exist", productId);
            throw new UnprocessableException(
                    "Product with id " + productId + " does not exist"
            );
        }
    }

    public void modifierExists(UUID modifierId) {
        if (!productModifierRepository.existsById(modifierId)) {
            logger.error("Modifier with id {} does not exist", modifierId);
            throw new UnprocessableException(
                    "Modifier with id " + modifierId + " does not exist"
            );
        }
    }

    public boolean compatibleModifierExists(UUID productId, UUID modifierIds) {
        return productCompatibleModifierRepository.existsByProductIdAndModifierId(productId, modifierIds);
    }
}
