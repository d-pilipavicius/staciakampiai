package com.example.demo.helper.validator;

import com.example.demo.orders.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductValidator {

    private final ProductRepository productRepository;

     public ProductValidator(ProductRepository productRepository) {
         this.productRepository = productRepository;
     }

     public boolean productsExist(List<UUID> productIds) {
         long count = productRepository.countByIdIn(productIds);
         return count == productIds.size();
     }
}
