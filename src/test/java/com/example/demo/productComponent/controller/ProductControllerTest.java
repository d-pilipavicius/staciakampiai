package com.example.demo.productComponent.controller;

import com.example.demo.helper.enums.Currency;
import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.PutProductDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.api.endpoints.ProductsController;
import com.example.demo.productComponent.applicationServices.ProductApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductsController.class)
@AutoConfigureMockMvc
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
                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(29.99)).currency(Currency.USD).build())
                .businessId(UUID.randomUUID())
                .build();

        postModifierDTO = PostModifierDTO.builder()
                .title("Test Modifier")
                .quantityInStock(50)
                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(5.99)).currency(Currency.USD).build())
                .businessId(UUID.randomUUID())
                .build();
    }


    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        // Arrange
        ProductDTO productDTO = ProductDTO.builder()
                .id(UUID.randomUUID())
                .title(postProductDTO.getTitle())
                .quantityInStock(postProductDTO.getQuantityInStock())
                .price(postProductDTO.getPrice())
                .businessId(postProductDTO.getBusinessId())
                .build();

        when(productApplicationService.createProduct(any(PostProductDTO.class))).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(post("/v1/products")
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postProductDTO)))
                .andExpect(status().isCreated());

        verify(productApplicationService, times(1)).createProduct(any(PostProductDTO.class));
    }

    @Test
    void shouldReturnNotFoundWhenProductCreationFails() throws Exception {
        // Arrange
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(productApplicationService).createProduct(any(PostProductDTO.class));

        // Act & Assert
        mockMvc.perform(post("/v1/products")
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postProductDTO)))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isNotFound());

        verify(productApplicationService, times(1)).createProduct(any(PostProductDTO.class));
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
                                .price(postProductDTO.getPrice())
                                .businessId(businessId)
                                .build(),
                        ProductDTO.builder()
                                .id(UUID.randomUUID())
                                .title("Product 2")
                                .quantityInStock(100)
                                .price(postProductDTO.getPrice())
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
                .andExpect(status().isOk());

        verify(productApplicationService, times(1))
                .getProductsByBusinessId(anyInt(), anyInt(), eq(businessId));
    }

    @Test
    void shouldReturnNotFoundWhenRetrievingProductsFails() throws Exception {
        // Arrange
        UUID businessId = UUID.randomUUID();
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(productApplicationService).getProductsByBusinessId(anyInt(), anyInt(), eq(businessId));

        // Act & Assert
        mockMvc.perform(get("/v1/products")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("businessId", businessId.toString())
                        .param("employeeId", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        verify(productApplicationService, times(1))
                .getProductsByBusinessId(anyInt(), anyInt(), eq(businessId));
    }

    @Test
    void shouldUpdateProductSuccessfully() throws Exception {
        // Arrange
        PutProductDTO putProductDTO = PutProductDTO.builder()
                .title("Updated Product")
                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(39.99)).currency(Currency.USD).build())
                .quantityInStock(150)
                .build();

        ProductDTO responseProductDTO = ProductDTO.builder()
                        .id(UUID.randomUUID())
                        .title("Updated Product")
                        .quantityInStock(150)
                        .price(postProductDTO.getPrice())
                        .businessId(postProductDTO.getBusinessId())
                        .build();

        when(productApplicationService.updateProduct(any(PutProductDTO.class), any(UUID.class)))
                .thenReturn(responseProductDTO);

        // Act & Assert
        mockMvc.perform(patch("/v1/products/{productId}", UUID.randomUUID())
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putProductDTO)))
                .andExpect(status().isOk());

        verify(productApplicationService, times(1))
                .updateProduct(any(PutProductDTO.class), any(UUID.class));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonexistentProduct() throws Exception {
        // Arrange
        PutProductDTO putProductDTO = PutProductDTO.builder()
                .title("Nonexistent Product")
                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(39.99)).currency(Currency.USD).build())
                .build();

        UUID productId = UUID.randomUUID();
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(productApplicationService).updateProduct(any(PutProductDTO.class), eq(productId));

        // Act & Assert
        mockMvc.perform(patch("/v1/products/{productId}", productId)
                        .param("employeeId", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putProductDTO)))
                .andExpect(status().isNotFound());

        verify(productApplicationService, times(1)).updateProduct(any(PutProductDTO.class), eq(productId));
    }

    @Test
    void shouldDeleteProductSuccessfully() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/v1/products/{productId}", UUID.randomUUID())
                        .param("employeeId", UUID.randomUUID().toString()))
                .andExpect(status().isNoContent());

        verify(productApplicationService, times(1)).deleteProduct(any(UUID.class));
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonexistentProduct() throws Exception {
        // Arrange
        UUID productId = UUID.randomUUID();
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(productApplicationService).deleteProduct(eq(productId));

        // Act & Assert
        mockMvc.perform(delete("/v1/products/{productId}", productId)
                        .param("employeeId", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        verify(productApplicationService, times(1)).deleteProduct(eq(productId));
    }

}



