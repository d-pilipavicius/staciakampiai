package com.example.demo.productComponent.domain.services;

import com.example.demo.productComponent.domain.entities.ProductCompatibleModifier;
import com.example.demo.productComponent.helper.mapper.ProductMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.productComponent.helper.validator.ProductValidator;
import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.PatchProductDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.api.dtos.ResponseProductDTO;
import com.example.demo.productComponent.domain.entities.Product;
import com.example.demo.productComponent.repository.ProductCompatibleModifierRepository;
import com.example.demo.productComponent.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final ProductCompatibleModifierRepository productCompatibleModifierRepository;

    @Transactional
    public ProductDTO createProduct(PostProductDTO postProductDTO){
        // Map the DTO to the model
        Product product = Mapper.mapToModel(postProductDTO, ProductMapper.TO_MODEL);

        // Save the product
        Product savedProduct = productRepository.save(product);

        // Add the product modifiers to the product
        postProductDTO.getCompatibleModifierIds().forEach(modifierId ->
            addModifierToProduct(savedProduct.getId(), modifierId)
        );

        // Save the product and return the DTO
        return Mapper.mapToDTO(savedProduct, ProductMapper.TO_DTO);
    }

    public GetProductsDTO getProductsByBusinessId(UUID businessId){
        // Get all the products by business id
        List<Product> products = productRepository.findAllByBusinessId(businessId);

        // Map the products to DTOs
        List<ProductDTO> productDTOS = Mapper.mapToDTOList(products, ProductMapper.TO_DTO);

        return GetProductsDTO.builder()
                .items(productDTOS)
                .businessId(businessId)
                .totalItems(productDTOS.size())
                .build();
    }

    public GetProductsDTO getProductsByBusinessId(int page, int size, UUID businessId){
        // Get all the products by business id
        Page<Product> products = productRepository.findAllByBusinessId(businessId, PageRequest.of(page, size));

        // Map the products to DTOs
        Page<ProductDTO> productDTOS = Mapper.mapToDTOPage(products, ProductMapper.TO_DTO);

        return GetProductsDTO.builder()
                .items(productDTOS.getContent())
                .businessId(businessId)
                .totalItems((int) productDTOS.getTotalElements())
                .currentPage(productDTOS.getPageable().getPageNumber())
                .totalPages(productDTOS.getTotalPages())
                .build();
    }

    @Transactional
    public ResponseProductDTO updateProduct(PatchProductDTO patchProductDTO, UUID productId){
        // Validate that if the data is provided -> it should be valid
        productValidator.validatePatchProductDTO(patchProductDTO);

        // Fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Apply updates to the product
        applyProductUpdates(patchProductDTO, product);

        // Save the updated product
        Product updatedProduct = productRepository.save(product);

        // Map the updated product to DTO
        ProductDTO productDTO = Mapper.mapToDTO(updatedProduct, ProductMapper.TO_DTO);

        return ResponseProductDTO.builder()
                .product(productDTO)
                .build();
    }

    // todo: add validation to check if the product/modifier exists before deleting it
    @Transactional
    public void deleteProduct(UUID productId){
        productCompatibleModifierRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }

    //todo: also adjust the validation logic here to accept null values for the optional fields -> if value is null -> change it to null, otherwise default to Optional.empty() for non nullable fields
    @Transactional
    protected void applyProductUpdates(PatchProductDTO patchProductDTO, Product product) {
        patchProductDTO.getTitle().ifPresent(product::setTitle);
        patchProductDTO.getQuantityInStock().ifPresent(product::setQuantityInStock);
        patchProductDTO.getPrice().ifPresent(moneyDTO -> {
            product.setPrice(moneyDTO.getAmount());
            product.setCurrency(moneyDTO.getCurrency());
        });

        if (patchProductDTO.getCompatibleModifierIds().isEmpty()) {
            return;
        }

        patchProductDTO.getCompatibleModifierIds().ifPresent(modifierIds -> {
            // Find current product modifiers
            List<UUID> currentModifierIds = productCompatibleModifierRepository.findModifierIdsByProductId(product.getId());

            // find modifiers to add
            List<UUID> modifiersToAdd = modifierIds.stream()
                    .filter(modifierId -> !currentModifierIds.contains(modifierId))
                    .toList();

            // find modifiers to remove
            List<UUID> modifiersToRemove = currentModifierIds.stream()
                    .filter(modifierId -> !modifierIds.contains(modifierId))
                    .toList();

            // Add new modifiers
            modifiersToAdd.forEach(modifierId -> addModifierToProduct(product.getId(), modifierId));

            // Remove old modifiers
            modifiersToRemove.forEach(modifierId -> removeModifierFromProduct(product.getId(), modifierId));
        });
    }

    private void validateModifiers(List<UUID> modifierIds) {
        if(!productValidator.modifiersExist(modifierIds)){
            logger.error("Some of the modifiers are not found");
            throw new IllegalArgumentException("Some of the modifiers are not found");
        }
    }

    @Transactional
    public void addModifierToProduct(UUID productId, UUID modifierId){
        // Check if the compatible product modifier already exists
        if(productValidator.compatibleModifierExists(productId, modifierId)){
            logger.error("Product with id {} is already compatible with modifier with id {}", productId, modifierId);
            throw new IllegalArgumentException("Product with id " + productId + " is already compatible with modifier with id " + modifierId);
        }

        // Check if the product and modifier exist
        productValidator.productExists(productId);
        productValidator.modifierExists(modifierId);

        // Build and save the product compatible modifier
        ProductCompatibleModifier productCompatibleModifier = ProductCompatibleModifier
                .builder()
                .productId(productId)
                .modifierId(modifierId)
                .build();

        productCompatibleModifierRepository.save(productCompatibleModifier);
    }

    @Transactional
    public void removeModifierFromProduct(UUID productId, UUID modifierId) {
        // Delete compatible product modifier
        productCompatibleModifierRepository.deleteByProductIdAndModifierId(productId, modifierId);
    }

    public boolean validateProductIds(List<UUID> productIds) {
        return productValidator.productsExist(productIds);
    }

    // If returns false, then the stock was not updated -> you should retry fetching the product and updating the stock
    @Transactional
    public boolean updateProductStock(UUID productId, int newStock) {
        try{
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            product.setQuantityInStock(newStock);
            productRepository.save(product);
            return true;
        } catch (OptimisticLockException e) {
            logger.error("Failed to update stock for product with id {}", productId);
            return false;
        }
    }
}
