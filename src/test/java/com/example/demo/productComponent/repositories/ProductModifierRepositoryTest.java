package com.example.demo.productComponent.repositories;

import com.example.demo.helper.enums.Currency;
import com.example.demo.productComponent.domain.entities.ProductModifier;
import com.example.demo.productComponent.repository.ProductModifierRepository;
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
public class ProductModifierRepositoryTest {

    @Autowired
    private ProductModifierRepository productModifierRepository;

    private UUID businessId;

    @BeforeEach
    void setup() {
        businessId = UUID.randomUUID();
    }

    @Test
    void shouldSaveAndRetrieveProductModifier() {
        // Arrange
        ProductModifier modifier = createProductModifier("Test Modifier", 50, BigDecimal.valueOf(5.99), Currency.USD);

        // Act
        ProductModifier savedModifier = productModifierRepository.save(modifier);
        Optional<ProductModifier> retrievedModifier = productModifierRepository.findById(savedModifier.getId());

        // Assert
        assertTrue(retrievedModifier.isPresent());
        assertEquals("Test Modifier", retrievedModifier.get().getTitle());
        assertEquals(50, retrievedModifier.get().getQuantityInStock());
        assertEquals(BigDecimal.valueOf(5.99), retrievedModifier.get().getPrice());
        assertEquals(Currency.USD, retrievedModifier.get().getCurrency());
    }

    @Test
    void shouldCountByIdIn() {
        // Arrange
        ProductModifier modifier1 = productModifierRepository.save(createProductModifier("Modifier 1", 100, BigDecimal.valueOf(10.00), Currency.USD));
        ProductModifier modifier2 = productModifierRepository.save(createProductModifier("Modifier 2", 150, BigDecimal.valueOf(15.00), Currency.EUR));

        List<UUID> modifierIds = List.of(modifier1.getId(), modifier2.getId());

        // Act
        long count = productModifierRepository.countByIdIn(modifierIds);

        // Assert
        assertEquals(2, count);
    }

    @Test
    void shouldFindAllByBusinessIdWithPagination() {
        // Arrange
        for (int i = 0; i < 5; i++) {
            productModifierRepository.save(createProductModifier("Modifier " + i, 10 * i, BigDecimal.valueOf(2.99 * i), Currency.USD));
        }

        // Act
        Page<ProductModifier> page = productModifierRepository.findAllByBusinessId(PageRequest.of(0, 3), businessId);

        // Assert
        assertEquals(3, page.getContent().size());
        assertEquals(2, page.getTotalPages());
        assertEquals("Modifier 0", page.getContent().get(0).getTitle());
    }

    @Test
    void shouldReturnEmptyWhenFindingByIdWithInvalidId() {
        // Arrange
        UUID invalidId = UUID.randomUUID();

        // Act
        Optional<ProductModifier> modifier = productModifierRepository.findById(invalidId);

        // Assert
        assertFalse(modifier.isPresent());
    }

    @Test
    void shouldDeleteProductModifier() {
        // Arrange
        ProductModifier modifier = productModifierRepository.save(createProductModifier("Modifier to Delete", 20, BigDecimal.valueOf(5.00), Currency.USD));

        UUID modifierId = modifier.getId();

        // Act
        productModifierRepository.deleteById(modifierId);
        Optional<ProductModifier> deletedModifier = productModifierRepository.findById(modifierId);

        // Assert
        assertFalse(deletedModifier.isPresent());
    }

    private ProductModifier createProductModifier(String title, int quantityInStock, BigDecimal price, Currency currency) {
        return ProductModifier.builder()
                .businessId(businessId)
                .title(title)
                .quantityInStock(quantityInStock)
                .price(price)
                .currency(currency)
                .rowVersion(new byte[]{1})
                .build();
    }
}


