package com.example.demo.productComponent.domain.services;

import com.example.demo.productComponent.helper.mapper.ProductMapper;
import com.example.demo.productComponent.helper.mapper.ProductModifierMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.productComponent.helper.validator.ProductValidator;
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
import com.example.demo.productComponent.domain.entities.Product;
import com.example.demo.productComponent.domain.entities.ProductModifier;
import com.example.demo.productComponent.repository.ProductModifierRepository;
import com.example.demo.productComponent.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductModifierRepository productModifierRepository;
    private final ProductValidator productValidator;

    public ProductService(
            ProductRepository productRepository,
            ProductModifierRepository productModifierRepository,
            ProductValidator productValidator
    ) {
        this.productRepository = productRepository;
        this.productModifierRepository = productModifierRepository;
        this.productValidator = productValidator;
    }

    public boolean validateProductIds(List<UUID> productIds) {
        return productValidator.productsExist(productIds);
    }

    @Transactional
    public ProductDTO createProduct(PostProductDTO postProductDTO){
        if(!productValidator.modifiersExist(postProductDTO.getCompatibleModifierIds())){
            throw new IllegalArgumentException("Some of the modifiers are not found");
        }

        Product product = Mapper.mapToModel(
                postProductDTO,
                ProductMapper.TO_MODEL
        );

        product.setProductModifiers(
                productModifierRepository.findAllById(
                                postProductDTO.getCompatibleModifierIds()
                        )
        );

        return Mapper.mapToDTO(
                productRepository.save(product),
                ProductMapper.TO_DTO
        );
    }

    public ProductModifierDTO createProductModifier(PostModifierDTO postModifierDTO){
        return Mapper.mapToDTO(
                productModifierRepository.save(
                        Mapper.mapToModel(
                                postModifierDTO,
                                ProductModifierMapper.TO_MODEL
                        )
                ),
                ProductModifierMapper.TO_DTO
        );
    }

    public GetProductsDTO getAllProducts(){
        List<ProductDTO> productDTOS = productRepository.findAll().stream()
                .map(ProductMapper.TO_DTO::map)
                .toList();

        GetProductsDTO getProductsDTO = new GetProductsDTO();
        getProductsDTO.setItems(productDTOS);
        getProductsDTO.setBusinessId(productDTOS.get(0).getBusinessId());
        return getProductsDTO;
    }

    public GetProductsDTO getAllProducts(int page, int size){
        List<ProductDTO> productDTOS = productRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ProductMapper.TO_DTO::map)
                .toList();

        return new GetProductsDTO(
                productDTOS.size(),
                size,
                page,
                productDTOS,
                productDTOS.get(0).getBusinessId()
        );
    }

    public GetModifiersDTO getAllProductModifiers(){
        return new GetModifiersDTO(
                productModifierRepository.findAll().stream()
                        .map(ProductModifierMapper.TO_DTO::map)
                        .toList()
        );
    }

    public GetModifiersDTO getAllProductModifiers(int page, int size){
        List<ProductModifierDTO> productModifierDTOS = productModifierRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ProductModifierMapper.TO_DTO::map)
                .toList();

        return new GetModifiersDTO(
                productModifierDTOS.size(),
                size,
                page,
                productModifierDTOS
        );
    }

    @Transactional
    public ResponseProductDTO updateProduct(PatchProductDTO patchProductDTO, UUID id){
        Product product = productRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Product not found")
        );

        applyProductUpdates(patchProductDTO, product);

        return new ResponseProductDTO(
                Mapper.mapToDTO(
                        productRepository.save(product),
                        ProductMapper.TO_DTO
                )
        );
    }

    @Transactional
    public ResponseModifierDTO updateProductModifier(PatchModifierDTO patchModifierDTO, UUID id){
        ProductModifier productModifier = productModifierRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Product modifier not found")
        );

        applyModifierUpdates(patchModifierDTO, productModifier);

        return new ResponseModifierDTO(
                Mapper.mapToDTO(
                        productModifierRepository.save(productModifier),
                        ProductModifierMapper.TO_DTO
                )
        );
    }

    @Transactional
    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }

    @Transactional
    public void deleteProductModifier(UUID id){
        productModifierRepository.deleteById(id);
    }

    private List<ProductModifier> getModifiersByIds(List<UUID> modifierIds) {
        return productModifierRepository.findAllById(modifierIds);
    }

    private void applyProductUpdates(PatchProductDTO patchProductDTO, Product product) {
        patchProductDTO.getTitle().ifPresent(product::setTitle);
        patchProductDTO.getQuantityInStock().ifPresent(product::setQuantityInStock);
        patchProductDTO.getPrice().ifPresent(moneyDTO -> {
            product.setPrice(moneyDTO.getAmount());
            product.setCurrency(moneyDTO.getCurrency());
        });

        patchProductDTO.getCompatibleModifierIds().ifPresent(this::validateModifiers);
        patchProductDTO.getCompatibleModifierIds()
                .map(this::getModifiersByIds)
                .ifPresent(product::setProductModifiers);
    }

    private void applyModifierUpdates(PatchModifierDTO patchModifierDTO, ProductModifier modifier) {
        patchModifierDTO.getTitle().ifPresent(modifier::setTitle);
        patchModifierDTO.getQuantityInStock().ifPresent(modifier::setQuantityInStock);
        patchModifierDTO.getPrice().ifPresent(moneyDTO -> {
            modifier.setPrice(moneyDTO.getAmount());
            modifier.setCurrency(moneyDTO.getCurrency());
        });
    }

    private void validateModifiers(List<UUID> modifierIds) {
        if(!productValidator.modifiersExist(modifierIds)){
            throw new IllegalArgumentException("Some of the modifiers are not found");
        }
    }

}
