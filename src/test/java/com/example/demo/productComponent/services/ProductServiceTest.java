package com.example.demo.productComponent.services;

import com.example.demo.helper.enums.Currency;
import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.PatchProductDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.api.dtos.ResponseProductDTO;
import com.example.demo.productComponent.domain.entities.Product;
import com.example.demo.productComponent.domain.services.ProductService;
import com.example.demo.productComponent.helper.validator.ProductValidator;
import com.example.demo.productComponent.repository.ProductCompatibleModifierRepository;
import com.example.demo.productComponent.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private ProductCompatibleModifierRepository productCompatibleModifierRepository;

    @InjectMocks
    private ProductService productService;

    private UUID businessId;
    private UUID productId;
    private UUID modifierId;
    private PostProductDTO validPostProductDTO;
    private PatchProductDTO validPatchProductDTO;
    private Product validProduct;

    @BeforeEach
    void setUp() {
        businessId = UUID.randomUUID();
        productId = UUID.randomUUID();
        modifierId = UUID.randomUUID();

        validPostProductDTO = PostProductDTO.builder()
                .title("Test Product")
                .quantityInStock(50)
                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(29.99)).currency(Currency.USD).build())
                .businessId(businessId)
                .compatibleModifierIds(List.of(modifierId))
                .build();

        validPatchProductDTO = PatchProductDTO.builder()
                .title(Optional.of("Updated Product"))
                .quantityInStock(Optional.of(150))
                .compatibleModifierIds(Optional.empty())
                .price(Optional.empty())
                .build();

        validProduct = Product.builder()
                .id(productId)
                .title("Test Product")
                .quantityInStock(50)
                .price(BigDecimal.valueOf(29.99))
                .currency(Currency.USD)
                .businessId(businessId)
                .rowVersion(0)
                .build();
    }

    @Test
    void shouldCreateProductSuccessfully() {
        // Arrange
        when(productValidator.modifiersExist(validPostProductDTO.getCompatibleModifierIds())).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(validProduct);

        // Act
        ProductDTO productDTO = productService.createProduct(validPostProductDTO);

        // Assert
        assertNotNull(productDTO);
        assertEquals(validPostProductDTO.getTitle(), productDTO.getTitle());
        verify(productValidator, times(1)).modifiersExist(validPostProductDTO.getCompatibleModifierIds());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenCreatingProductWithInvalidModifiers() {
        // Arrange
        when(productValidator.modifiersExist(validPostProductDTO.getCompatibleModifierIds())).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(validPostProductDTO));
    }

    @Test
    void shouldGetProductsByBusinessId() {
        // Arrange
        List<Product> products = List.of(validProduct);
        when(productRepository.findAllByBusinessId(businessId)).thenReturn(products);

        // Act
        GetProductsDTO result = productService.getProductsByBusinessId(businessId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        verify(productRepository, times(1)).findAllByBusinessId(businessId);
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.of(validProduct));
        when(productRepository.save(any(Product.class))).thenReturn(validProduct);

        // Act
        ResponseProductDTO response = productService.updateProduct(validPatchProductDTO, productId);

        // Assert
        assertNotNull(response);
        assertEquals(validPatchProductDTO.getTitle().get(), response.getProduct().getTitle());
        verify(productRepository, times(1)).save(validProduct);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(validPatchProductDTO, productId));
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productCompatibleModifierRepository, times(1)).deleteByProductId(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void shouldUpdateProductStockSuccessfully() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.of(validProduct));
        when(productRepository.save(any(Product.class))).thenReturn(validProduct);

        // Act
        boolean result = productService.updateProductStock(productId, 20);

        // Assert
        assertTrue(result);
        assertEquals(20, validProduct.getQuantityInStock());
        verify(productRepository, times(1)).save(validProduct);
    }

    @Test
    void shouldFailToUpdateStockWhenOptimisticLockOccurs() {
        // Arrange
        when(productRepository.findById(productId)).thenThrow(OptimisticLockException.class);

        // Act
        boolean result = productService.updateProductStock(productId, 20);

        // Assert
        assertFalse(result);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionForInvalidTitleInPostProduct() {
        // Arrange
        PostProductDTO invalidPostProductDTO = PostProductDTO.builder()
                .title("") // Invalid title
                .quantityInStock(50)
                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(29.99)).currency(Currency.USD).build())
                .businessId(businessId)
                .compatibleModifierIds(List.of(modifierId))
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(invalidPostProductDTO));
    }
}


