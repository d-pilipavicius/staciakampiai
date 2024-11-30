package com.example.demo.productComponent.controller;

import com.example.demo.helper.enums.Currency;
import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PatchModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.ResponseModifierDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.api.endpoints.ProductsController;
import com.example.demo.productComponent.applicationServices.ProductApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductApplicationService productApplicationService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostProductDTO postProductDTO;
    private PostModifierDTO postModifierDTO;

    @BeforeEach
    void setup() {
        postProductDTO = PostProductDTO.builder()
                .title("Test Product")
                .quantityInStock(100)
                .price(new MoneyDTO(BigDecimal.valueOf(29.99), Currency.USD))
                .businessId(UUID.randomUUID())
                .build();

        postModifierDTO = PostModifierDTO.builder()
                .title("Test Modifier")
                .quantityInStock(50)
                .price(new MoneyDTO(BigDecimal.valueOf(5.99), Currency.USD))
                .businessId(UUID.randomUUID())
                .build();
    }


    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        // Arrange
        ProductDTO productDTO = ProductDTO.builder()
                .id(UUID.randomUUID())
                .title("Test Product")
                .quantityInStock(100)
                .price(new MoneyDTO(BigDecimal.valueOf(29.99), Currency.USD))
                .businessId(postProductDTO.getBusinessId())
                .build();

        when(productApplicationService.createProduct(any(PostProductDTO.class))).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(post("/v1/products")
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postProductDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Product"))
                .andExpect(jsonPath("$.quantityInStock").value(100));

        verify(productApplicationService, times(1)).createProduct(any(PostProductDTO.class));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidProductData() throws Exception {
        // Arrange
        PostProductDTO postProductDTO = new PostProductDTO(); // Missing required fields

        // Act & Assert
        mockMvc.perform(post("/v1/products")
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postProductDTO)))
                .andExpect(status().isBadRequest());

        verify(productApplicationService, never()).createProduct(any(PostProductDTO.class));
    }

    @Test
    void shouldGetProductsSuccessfully() throws Exception {
        // Arrange
        UUID businessId = UUID.randomUUID();
        GetProductsDTO productsDTO = GetProductsDTO.builder()
                .items(List.of(
                        ProductDTO.builder()
                                .id(UUID.randomUUID())
                                .title("Product 1")
                                .quantityInStock(50)
                                .price(new MoneyDTO(BigDecimal.valueOf(19.99), Currency.USD))
                                .businessId(businessId)
                                .build(),
                        ProductDTO.builder()
                                .id(UUID.randomUUID())
                                .title("Product 2")
                                .quantityInStock(100)
                                .price(new MoneyDTO(BigDecimal.valueOf(39.99), Currency.USD))
                                .businessId(businessId)
                                .build()
                ))
                .build();

        when(productApplicationService.getProductsByBusinessId(anyInt(), anyInt(), eq(businessId)))
                .thenReturn(productsDTO);

        // Act & Assert
        mockMvc.perform(get("/v1/products")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("businessId", businessId.toString())
                        .param("employeeId", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].title").value("Product 1"));

        verify(productApplicationService, times(1))
                .getProductsByBusinessId(anyInt(), anyInt(), eq(businessId));
    }

    @Test
    void shouldCreateProductModifierSuccessfully() throws Exception {
        // Arrange
        ProductModifierDTO productModifierDTO = ProductModifierDTO.builder()
                .id(UUID.randomUUID())
                .title("Test Modifier")
                .quantityInStock(50)
                .price(new MoneyDTO(BigDecimal.valueOf(5.99), Currency.USD))
                .businessId(postModifierDTO.getBusinessId())
                .build();

        when(productApplicationService.createModifier(any(PostModifierDTO.class))).thenReturn(productModifierDTO);

        // Act & Assert
        mockMvc.perform(post("/v1/products/modifiers")
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postModifierDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Modifier"))
                .andExpect(jsonPath("$.quantityInStock").value(50));

        verify(productApplicationService, times(1)).createModifier(any(PostModifierDTO.class));
    }

    @Test
    void shouldUpdateProductModifierSuccessfully() throws Exception {
        // Arrange
        PatchModifierDTO patchModifierDTO = new PatchModifierDTO();
        patchModifierDTO.setTitle(Optional.of("Updated Modifier"));

        ResponseModifierDTO responseModifierDTO = ResponseModifierDTO.builder()
                .productModifier(ProductModifierDTO.builder()
                        .id(UUID.randomUUID())
                        .title("Updated Modifier")
                        .quantityInStock(100)
                        .price(new MoneyDTO(BigDecimal.valueOf(9.99), Currency.USD))
                        .businessId(UUID.randomUUID())
                        .build())
                .build();

        when(productApplicationService.updateProductModifier(any(PatchModifierDTO.class), any(UUID.class)))
                .thenReturn(responseModifierDTO);

        // Act & Assert
        mockMvc.perform(patch("/v1/products/modifiers/{modifierId}", UUID.randomUUID())
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchModifierDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productModifier.title").value("Updated Modifier"));

        verify(productApplicationService, times(1))
                .updateProductModifier(any(PatchModifierDTO.class), any(UUID.class));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        // Arrange
        UUID productId = UUID.randomUUID();
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(productApplicationService)
                .getProductsByBusinessId(anyInt(), anyInt(), eq(productId));

        // Act & Assert
        mockMvc.perform(get("/v1/products")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("businessId", productId.toString())
                        .param("employeeId", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenBusinessIdIsMissing() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/v1/products")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("employeeId", UUID.randomUUID().toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnEmptyProductsWhenNoProductsExist() throws Exception {
        // Arrange
        UUID businessId = UUID.randomUUID();
        GetProductsDTO emptyProductsDTO = GetProductsDTO.builder().items(Collections.emptyList()).build();

        when(productApplicationService.getProductsByBusinessId(anyInt(), anyInt(), eq(businessId)))
                .thenReturn(emptyProductsDTO);

        // Act & Assert
        mockMvc.perform(get("/v1/products")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("businessId", businessId.toString())
                        .param("employeeId", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(0));

        verify(productApplicationService, times(1)).getProductsByBusinessId(anyInt(), anyInt(), eq(businessId));
    }

    @Test
    void shouldReturnNotFoundWhenModifierIdDoesNotExist() throws Exception {
        // Arrange
        UUID modifierId = UUID.randomUUID();
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(productApplicationService)
                .updateProductModifier(any(PatchModifierDTO.class), eq(modifierId));

        PatchModifierDTO patchModifierDTO = new PatchModifierDTO();
        patchModifierDTO.setTitle(Optional.of("Nonexistent Modifier"));

        // Act & Assert
        mockMvc.perform(patch("/v1/products/modifiers/{modifierId}", modifierId)
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchModifierDTO)))
                .andExpect(status().isNotFound());

        verify(productApplicationService, times(1)).updateProductModifier(any(PatchModifierDTO.class), eq(modifierId));
    }

}



