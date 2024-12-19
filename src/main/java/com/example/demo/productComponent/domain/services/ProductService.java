package com.example.demo.productComponent.domain.services;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnprocessableException;
import com.example.demo.productComponent.api.dtos.ProductCompatibleModifierDTO;
import com.example.demo.productComponent.domain.entities.ProductCompatibleModifier;
import com.example.demo.productComponent.helper.factories.ProductFactory;
import com.example.demo.productComponent.helper.mapper.ProductCompatibleModifierMapper;
import com.example.demo.productComponent.helper.mapper.ProductMapper;
import com.example.demo.CommonHelper.mapper.base.Mapper;
import com.example.demo.productComponent.helper.validator.ProductValidator;
import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.PutProductDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
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
    public ProductDTO createProduct(PostProductDTO postProductDTO) {
        // Map the DTO to the model
        Product product = Mapper.mapToModel(postProductDTO, ProductMapper.TO_MODEL);

        // Save the product
        Product savedProduct = productRepository.save(product);

        // Add the product modifiers to the product
        if (postProductDTO.getCompatibleModifierIds() != null) {
            postProductDTO.getCompatibleModifierIds()
                    .forEach(modifierId -> addModifierToProduct(savedProduct.getId(), modifierId));
        }

        // Save the product and return the DTO
        return Mapper.mapToDTO(savedProduct, ProductMapper.TO_DTO);
    }

    public GetProductsDTO getProductsByBusinessId(UUID businessId) {
        // Get all the products by business id
        List<Product> products = productRepository.findAllByBusinessId(businessId);

        // Map the products to DTOs
        List<ProductDTO> productDTOS = Mapper.mapToDTOList(products, ProductMapper.TO_DTO);

        // Build and return the DTO
        return ProductMapper.listToGetProductsDTO(businessId, productDTOS);
    }

    public GetProductsDTO getProductsByBusinessId(int page, int size, UUID businessId) {
        // Get all the products by business id
        Page<Product> products = productRepository.findAllByBusinessId(businessId, PageRequest.of(page, size));

        // Map the products to DTOs
        Page<ProductDTO> productDTOS = Mapper.mapToDTOPage(products, ProductMapper.TO_DTO);

        // Build and return the DTO
        return ProductMapper.pageToGetProducts(businessId, productDTOS);
    }

    public List<ProductDTO> getProductsByListOfId(List<UUID> productIds){
        List<Product> products = productRepository.findByIdIn(productIds);
        return Mapper.mapToDTOList(products, ProductMapper.TO_DTO);
    }

    @Transactional
    public ProductDTO updateProduct(PutProductDTO putProductDTO, UUID productId) {
        // Validate the modifiers
        productValidator.modifiersExist(putProductDTO.getCompatibleModifierIds());

        // Fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "Product with id " + productId + " not found"));

        // Apply updates to the product
        applyProductUpdates(putProductDTO, product);

        // Save the updated product
        Product updatedProduct = productRepository.save(product);

        // Map the updated product to DTO and return it
        return Mapper.mapToDTO(updatedProduct, ProductMapper.TO_DTO);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        productValidator.productExists(productId);
        productCompatibleModifierRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }

    @Transactional
    protected void applyProductUpdates(PutProductDTO putProductDTO, Product product) {
        // The data should be valid at this point
        product.setTitle(putProductDTO.getTitle());
        product.setQuantityInStock(putProductDTO.getQuantityInStock());
        product.setPrice(putProductDTO.getPrice().getAmount());
        product.setCurrency(putProductDTO.getPrice().getCurrency());

        // Find current product modifiers
        List<UUID> currentModifierIds = productCompatibleModifierRepository.findModifierIdsByProductId(product.getId());

        // find modifiers to add
        List<UUID> modifiersToAdd = putProductDTO.getCompatibleModifierIds().stream()
                .filter(modifierId -> !currentModifierIds.contains(modifierId))
                .toList();

        // find modifiers to remove
        List<UUID> modifiersToRemove = currentModifierIds.stream()
                .filter(modifierId -> !putProductDTO.getCompatibleModifierIds().contains(modifierId))
                .toList();

        // Add new modifiers
        modifiersToAdd.forEach(modifierId -> addModifierToProduct(product.getId(), modifierId));

        // Remove old modifiers
        modifiersToRemove.forEach(modifierId -> removeModifierFromProduct(product.getId(), modifierId));
    }

    private void validateModifiers(List<UUID> modifierIds) {
        if (!productValidator.modifiersExist(modifierIds)) {
            logger.error("Some of the modifiers are not found");
            throw new NotFoundException(
                    "Some of the modifiers are not found");
        }
    }

    @Transactional
    public void addModifierToProduct(UUID productId, UUID modifierId) {
        // Check if the compatible product modifier already exists
        if (productValidator.compatibleModifierExists(productId, modifierId)) {
            logger.error("Product with id {} is already compatible with modifier with id {}", productId, modifierId);
            throw new UnprocessableException(
                    "Product is already compatible with the modifier");
        }

        // Check if the product and modifier exist
        productValidator.productExists(productId);
        productValidator.modifierExists(modifierId);

        // Create the DTO
        ProductCompatibleModifierDTO productCompatibleModifierDTO = ProductFactory
                .createProductCompatibleModifierDTO(productId, modifierId);

        // Map the DTO to the model
        ProductCompatibleModifier productCompatibleModifier = Mapper.mapToDTO(productCompatibleModifierDTO,
                ProductCompatibleModifierMapper.TO_MODEL);

        // Save the compatible product modifier
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

    // If returns false, then the stock was not updated -> you should retry fetching
    // the product and updating the stock
    @Transactional
    public boolean updateProductStock(UUID productId, int newStock) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException(
                            "Product with id " + productId + " not found"));

            product.setQuantityInStock(newStock);
            productRepository.save(product);
            return true;
        } catch (OptimisticLockException e) {
            logger.error("Failed to update stock for product with id {}", productId);
            return false;
        }
    }

    public ProductDTO getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "Product with id " + productId + " not found"
                ));

        return Mapper.mapToDTO(product, ProductMapper.TO_DTO);
    }
}
