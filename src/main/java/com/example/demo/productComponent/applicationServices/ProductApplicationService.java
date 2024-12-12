package com.example.demo.productComponent.applicationServices;

import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.GetModifiersDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PutModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.PutProductDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.domain.services.ProductModifierService;
import com.example.demo.productComponent.domain.services.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ProductApplicationService.class);

    private final ProductService productService;
    private final ProductModifierService productModifierService;

    @Transactional
    public ProductDTO createProduct(PostProductDTO postProductDTO) {
        // Create a product, here the product and the specified modifiers are also linked
        ProductDTO productDTO = productService.createProduct(postProductDTO);

        // Set productModifierDto in productDTO here because ProductModifiers are taken from the ProductModifierService
        List<ProductModifierDTO> productModifierDTOS = productModifierService.getModifiersByProductId(productDTO.getId()).getItems();
        productDTO.setCompatibleModifiers(productModifierDTOS);

        return productDTO;
    }

    @Transactional
    public ProductModifierDTO createModifier(PostModifierDTO postModifierDTO) {
        return productModifierService.createModifier(postModifierDTO);
    }

    public GetProductsDTO getProductsByBusinessId(UUID businessId) {
        // Get all products by business id
        GetProductsDTO productsDTO = productService.getProductsByBusinessId(businessId);

        // Get all product modifiers for each product
        productsDTO.getItems().forEach(productDTO -> productDTO.setCompatibleModifiers(
                productModifierService.getModifiersByProductId(productDTO.getId()).getItems()
        ));

        return productsDTO;
    }

    public GetProductsDTO getProductsByBusinessId(int page, int size, UUID businessId) {
        // Get all products by business id
        GetProductsDTO productsDTO = productService.getProductsByBusinessId(page, size, businessId);

        // Get all product modifiers for each product
        productsDTO.getItems().forEach(productDTO -> productDTO.setCompatibleModifiers(
                productModifierService.getModifiersByProductId(productDTO.getId()).getItems()
        ));

        return productsDTO;
    }

    public GetModifiersDTO getModifiersByBusinessId(int page, int size, UUID businessId) {
        return productModifierService.getModifiersByBusinessId(page, size, businessId);
    }

    public GetModifiersDTO getModifiersByProductId(UUID productId) {
        return productModifierService.getModifiersByProductId(productId);
    }

    @Transactional
    public ProductDTO updateProduct(PutProductDTO putProductDTO, UUID productId) {
        // Update and the product
        ProductDTO responseProductDTO = productService.updateProduct(putProductDTO, productId);

        // Set productModifierDTOs in ResponseProductDTO here because Product entity does not have a direct field with productModifier objects
        List<ProductModifierDTO> productModifierDTOS = productModifierService.getModifiersByProductId(responseProductDTO.getId()).getItems();
        responseProductDTO.setCompatibleModifiers(productModifierDTOS);

        return responseProductDTO;
    }

    @Transactional
    public ProductModifierDTO updateProductModifier(PutModifierDTO putModifierDTO, UUID modifierId) {
        return productModifierService.updateModifier(putModifierDTO, modifierId);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        productService.deleteProduct(productId);
    }

    @Transactional
    public void deleteProductModifier(UUID modifierId) {
        productModifierService.deleteModifier(modifierId);
    }

    // Helper methods for other components
    public boolean validateProductIds(List<UUID> productIds) {
        return productService.validateProductIds(productIds);
    }

    @Transactional
    public boolean updateProductStock(UUID productId, int newStock) {
        return productService.updateProductStock(productId, newStock);
    }
}
