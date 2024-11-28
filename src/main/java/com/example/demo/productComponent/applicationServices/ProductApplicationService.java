package com.example.demo.productComponent.applicationServices;

import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.GetModifiersDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PatchModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.ResponseModifierDTO;
import com.example.demo.productComponent.api.dtos.PatchProductDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.api.dtos.ResponseProductDTO;
import com.example.demo.productComponent.domain.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ProductApplicationService.class);

    private final ProductService productService;

    public ProductApplicationService(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    public ProductDTO createProduct(PostProductDTO postProductDTO) {
        return productService.createProduct(postProductDTO);
    }

    @Transactional
    public ProductModifierDTO createProductModifier(PostModifierDTO postModifierDTO) {
        return productService.createProductModifier(postModifierDTO);
    }

    public GetProductsDTO getAllProducts() {
        return productService.getAllProducts();
    }

    public GetProductsDTO getAllProducts(int page, int size) {
        return productService.getAllProducts(page, size);
    }

    public GetModifiersDTO getAllProductModifiers() {
        return productService.getAllProductModifiers();
    }

    public GetModifiersDTO getAllProductModifiers(int page, int size) {
        return productService.getAllProductModifiers(page, size);
    }

    @Transactional
    public ResponseProductDTO updateProduct(PatchProductDTO patchProductDTO, UUID id) {
        return productService.updateProduct(patchProductDTO, id);
    }

    @Transactional
    public ResponseModifierDTO updateProductModifier(PatchModifierDTO patchModifierDTO, UUID id) {
        return productService.updateProductModifier(patchModifierDTO, id);
    }

    @Transactional
    public void deleteProduct(UUID id) {
        productService.deleteProduct(id);
    }

    @Transactional
    public void deleteProductModifier(UUID id) {
        productService.deleteProductModifier(id);
    }

    public boolean validateProductIds(List<UUID> productIds) {
        return productService.validateProductIds(productIds);
    }
}
