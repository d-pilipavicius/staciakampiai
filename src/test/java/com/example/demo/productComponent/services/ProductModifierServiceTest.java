package com.example.demo.productComponent.services;

import com.example.demo.helper.enums.Currency;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.GetModifiersDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PatchModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.ResponseModifierDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.domain.entities.ProductModifier;
import com.example.demo.productComponent.domain.services.ProductModifierService;
import com.example.demo.productComponent.helper.validator.ProductModifierValidator;
import com.example.demo.productComponent.repository.ProductCompatibleModifierRepository;
import com.example.demo.productComponent.repository.ProductModifierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductModifierServiceTest {

    @Mock
    private ProductModifierRepository productModifierRepository;

    @Mock
    private ProductCompatibleModifierRepository productCompatibleModifierRepository;

    @Mock
    private ProductModifierValidator productModifierValidator;

    @InjectMocks
    private ProductModifierService productModifierService;

    private UUID businessId;
    private UUID modifierId;
    private ProductModifier validModifier;
    private PostModifierDTO validPostModifierDTO;
    private PatchModifierDTO validPatchModifierDTO;

    @BeforeEach
    void setUp() {
        businessId = UUID.randomUUID();
        modifierId = UUID.randomUUID();

        validPostModifierDTO = PostModifierDTO.builder()
                .title("Test Modifier")
                .quantityInStock(100)
                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(9.99)).currency(Currency.USD).build())
                .businessId(businessId)
                .build();

        validModifier = ProductModifier.builder()
                .id(modifierId)
                .title("Test Modifier")
                .quantityInStock(100)
                .price(BigDecimal.valueOf(9.99))
                .currency(Currency.USD)
                .businessId(businessId)
                .rowVersion(0)
                .build();

        validPatchModifierDTO = new PatchModifierDTO();
        validPatchModifierDTO.setTitle(Optional.of("Updated Modifier"));
        validPatchModifierDTO.setQuantityInStock(Optional.of(200));
        validPatchModifierDTO.setPrice(Optional.of(
                MoneyDTO.builder().amount(BigDecimal.valueOf(19.99)).currency(Currency.USD).build()
        ));
    }

    @Test
    void shouldCreateModifierSuccessfully() {
        // Arrange
        when(productModifierRepository.save(any(ProductModifier.class))).thenReturn(validModifier);

        // Act
        ProductModifierDTO result = productModifierService.createModifier(validPostModifierDTO);

        // Assert
        assertNotNull(result);
        assertEquals(validPostModifierDTO.getTitle(), result.getTitle());
        verify(productModifierValidator, times(1)).validatePostModifierDTO(validPostModifierDTO);
        verify(productModifierRepository, times(1)).save(any(ProductModifier.class));
    }

    @Test
    void shouldGetModifiersByProductId() {
        // Arrange
        List<UUID> modifierIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(productCompatibleModifierRepository.findModifierIdsByProductId(modifierId)).thenReturn(modifierIds);
        when(productModifierRepository.findAllById(modifierIds)).thenReturn(List.of(validModifier, validModifier));

        // Act
        GetModifiersDTO result = productModifierService.getModifiersByProductId(modifierId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        verify(productCompatibleModifierRepository, times(1)).findModifierIdsByProductId(modifierId);
        verify(productModifierRepository, times(1)).findAllById(modifierIds);
    }

    @Test
    void shouldGetModifiersByBusinessIdWithPagination() {
        // Arrange
        Page<ProductModifier> modifiersPage = new PageImpl<>(List.of(validModifier, validModifier));
        when(productModifierRepository.findAllByBusinessId(any(PageRequest.class), eq(businessId)))
                .thenReturn(modifiersPage);

        // Act
        GetModifiersDTO result = productModifierService.getModifiersByBusinessId(0, 2, businessId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        verify(productModifierRepository, times(1)).findAllByBusinessId(any(PageRequest.class), eq(businessId));
    }

    @Test
    void shouldUpdateModifierSuccessfully() {
        // Arrange
        when(productModifierRepository.findById(modifierId)).thenReturn(Optional.of(validModifier));
        when(productModifierRepository.save(any(ProductModifier.class))).thenReturn(validModifier);

        // Act
        ResponseModifierDTO result = productModifierService.updateModifier(validPatchModifierDTO, modifierId);

        // Assert
        assertNotNull(result);
        assertEquals(validPatchModifierDTO.getTitle().get(), result.getProductModifier().getTitle());
        verify(productModifierValidator, times(1)).validatePatchModifierDTO(validPatchModifierDTO);
        verify(productModifierRepository, times(1)).findById(modifierId);
        verify(productModifierRepository, times(1)).save(validModifier);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentModifier() {
        // Arrange
        when(productModifierRepository.findById(modifierId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> productModifierService.updateModifier(validPatchModifierDTO, modifierId));

        verify(productModifierRepository, times(1)).findById(modifierId);
        verify(productModifierRepository, never()).save(any(ProductModifier.class));
    }

    @Test
    void shouldDeleteModifierSuccessfully() {
        // Arrange
        // Act
        productModifierService.deleteModifier(modifierId);

        // Assert
        verify(productCompatibleModifierRepository, times(1)).deleteByModifierId(modifierId);
        verify(productModifierRepository, times(1)).deleteById(modifierId);
    }

    @Test
    void shouldGetModifiersByIds() {
        // Arrange
        List<UUID> modifierIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(productModifierRepository.findAllById(modifierIds)).thenReturn(List.of(validModifier, validModifier));

        // Act
        GetModifiersDTO result = productModifierService.getModifiersByIds(modifierIds);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        verify(productModifierRepository, times(1)).findAllById(modifierIds);
    }
}


