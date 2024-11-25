package com.example.demo.orders.services;

import com.example.demo.orders.API.DTOs.ProductDTOs.GetProductsDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.GetModifiersDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.PatchModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.PostModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.ResponseModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.PatchProductDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.PostProductDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ResponseProductDTO;
import com.example.demo.orders.domain.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
