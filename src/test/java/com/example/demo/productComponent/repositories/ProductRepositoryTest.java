package com.example.demo.productComponent.repositories;

import com.example.demo.helper.enums.Currency;
import com.example.demo.productComponent.domain.entities.Product;
import com.example.demo.productComponent.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private UUID businessId;

    @BeforeEach
    void setup() {
        businessId = UUID.randomUUID();
    }

    @Test
    void shouldSaveAndRetrieveProduct() {
        // Arrange
        Product product = createProduct("Test Product", 100, BigDecimal.valueOf(29.99), Currency.USD);

        // Act
        Product savedProduct = productRepository.save(product);
        Optional<Product> retrievedProduct = productRepository.findById(savedProduct.getId());

        // Assert
        assertTrue(retrievedProduct.isPresent());
        assertEquals("Test Product", retrievedProduct.get().getTitle());
        assertEquals(100, retrievedProduct.get().getQuantityInStock());
        assertEquals(Currency.USD, retrievedProduct.get().getCurrency());
    }

    @Test
    void shouldFindAllByBusinessId() {
        // Arrange
        Product product1 = productRepository.save(createProduct("Product 1", 50, BigDecimal.valueOf(19.99), Currency.USD));
        Product product2 = productRepository.save(createProduct("Product 2", 75, BigDecimal.valueOf(49.99), Currency.EUR));

        // Act
        List<Product> products = productRepository.findAllByBusinessId(businessId);

        // Assert
        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    void shouldFindAllByBusinessIdWithPagination() {
        // Arrange
        for (int i = 0; i < 5; i++) {
            productRepository.save(createProduct("Product " + i, 10 * i, BigDecimal.valueOf(9.99 * i), Currency.USD));
        }

        // Act
        Page<Product> page = productRepository.findAllByBusinessId(businessId, PageRequest.of(0, 3));

        // Assert
        assertEquals(3, page.getContent().size());
        assertEquals(2, page.getTotalPages());
        assertEquals("Product 0", page.getContent().get(0).getTitle());
    }

    @Test
    void shouldCountByIdIn() {
        // Arrange
        Product product1 = productRepository.save(createProduct("Product 1", 50, BigDecimal.valueOf(19.99), Currency.USD));
        Product product2 = productRepository.save(createProduct("Product 2", 75, BigDecimal.valueOf(49.99), Currency.EUR));

        List<UUID> ids = List.of(product1.getId(), product2.getId());

        // Act
        long count = productRepository.countByIdIn(ids);

        // Assert
        assertEquals(2, count);
    }

    @Test
    void shouldReturnEmptyWhenFindingByIdWithInvalidId() {
        // Arrange
        UUID invalidId = UUID.randomUUID();

        // Act
        Optional<Product> product = productRepository.findById(invalidId);

        // Assert
        assertFalse(product.isPresent());
    }

    private Product createProduct(String title, int quantityInStock, BigDecimal price, Currency currency) {
        return Product.builder()
                .businessId(businessId)
                .title(title)
                .quantityInStock(quantityInStock)
                .currency(currency)
                .price(price)
                .rowVersion(new byte[]{1})
                .build();
    }
}


