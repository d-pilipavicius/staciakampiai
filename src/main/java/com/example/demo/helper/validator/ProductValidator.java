package com.example.demo.helper.validator;

import com.example.demo.orders.repository.ProductModifierRepository;
import com.example.demo.orders.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductValidator {

    private final ProductRepository productRepository;
    private final ProductModifierRepository productModifierRepository;

     public ProductValidator(ProductRepository productRepository, ProductModifierRepository productModifierRepository) {
         this.productRepository = productRepository;
         this.productModifierRepository = productModifierRepository;
     }

     public boolean productsExist(List<UUID> productIds) {
         long count = productRepository.countByIdIn(productIds);
         return count == productIds.size();
     }

     public boolean modifiersExist(List<UUID> modifierIds) {
         long count = productModifierRepository.countByIdIn(modifierIds);
         return count == modifierIds.size();
     }
}
