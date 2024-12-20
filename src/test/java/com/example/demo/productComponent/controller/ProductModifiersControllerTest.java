package com.example.demo.productComponent.controller;

import com.example.demo.CommonHelper.enums.Currency;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.GetModifiersDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PutModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.MoneyDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductsController.class)
@AutoConfigureMockMvc
class ProductModifiersControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductApplicationService productApplicationService;

        @Autowired
        private ObjectMapper objectMapper;

        private PostModifierDTO validPostModifierDTO;
        private PutModifierDTO validPutModifierDTO;
        private UUID modifierId;
        private UUID businessId;

        @BeforeEach
        void setUp() {
                businessId = UUID.randomUUID();
                modifierId = UUID.randomUUID();

                validPostModifierDTO = PostModifierDTO.builder()
                                .title("Test Modifier")
                                .quantityInStock(100)
                                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(9.99)).currency(Currency.USD)
                                                .build())
                                .businessId(businessId)
                                .build();

                validPutModifierDTO = PutModifierDTO
                                .builder()
                                .title("Updated Modifier")
                                .quantityInStock(200)
                                .price(MoneyDTO.builder().amount(BigDecimal.valueOf(19.99)).currency(Currency.USD)
                                                .build())
                                .build();
        }

        @Test
        void shouldCreateModifierSuccessfully() throws Exception {
                // Arrange
                ProductModifierDTO modifierDTO = ProductModifierDTO.builder()
                                .id(modifierId)
                                .title(validPostModifierDTO.getTitle())
                                .quantityInStock(validPostModifierDTO.getQuantityInStock())
                                .price(validPostModifierDTO.getPrice())
                                .businessId(validPostModifierDTO.getBusinessId())
                                .build();

                when(productApplicationService.createModifier(any(PostModifierDTO.class))).thenReturn(modifierDTO);

                // Act & Assert
                mockMvc.perform(post("/v1/products/modifiers")
                                .param("employeeId", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validPostModifierDTO)))
                                .andExpect(status().isCreated());

                verify(productApplicationService, times(1)).createModifier(any(PostModifierDTO.class));
        }

        @Test
        void shouldReturnNotFoundWhenModifierCreationFails() throws Exception {
                // Arrange
                doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                                .when(productApplicationService).createModifier(any(PostModifierDTO.class));

                // Act & Assert
                mockMvc.perform(post("/v1/products/modifiers")
                                .param("employeeId", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validPostModifierDTO)))
                                .andExpect(status().isNotFound());

                verify(productApplicationService, times(1)).createModifier(any(PostModifierDTO.class));
        }

        @Test
        void shouldGetModifiersByBusinessIdSuccessfully() throws Exception {
                // Arrange
                GetModifiersDTO modifiersDTO = GetModifiersDTO.builder()
                                .items(List.of(
                                                ProductModifierDTO.builder()
                                                                .id(UUID.randomUUID())
                                                                .title("Modifier 1")
                                                                .quantityInStock(50)
                                                                .price(validPostModifierDTO.getPrice())
                                                                .businessId(businessId)
                                                                .build(),
                                                ProductModifierDTO.builder()
                                                                .id(UUID.randomUUID())
                                                                .title("Modifier 2")
                                                                .quantityInStock(100)
                                                                .price(validPostModifierDTO.getPrice())
                                                                .businessId(businessId)
                                                                .build()))
                                .build();

                when(productApplicationService.getModifiersByBusinessId(0, 10, businessId))
                                .thenReturn(modifiersDTO);

                // Act & Assert
                mockMvc.perform(get("/v1/products/modifiers")
                                .param("pageNumber", "0")
                                .param("pageSize", "10")
                                .param("businessId", businessId.toString())
                                .param("employeeId", UUID.randomUUID().toString()))
                                .andExpect(status().isOk());

                verify(productApplicationService, times(1)).getModifiersByBusinessId(0, 10, businessId);
        }

        @Test
        void shouldReturnNotFoundWhenGettingModifiersFails() throws Exception {
                // Arrange
                doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                                .when(productApplicationService).getModifiersByBusinessId(0, 10, businessId);

                // Act & Assert
                mockMvc.perform(get("/v1/products/modifiers")
                                .param("pageNumber", "0")
                                .param("pageSize", "10")
                                .param("businessId", businessId.toString())
                                .param("employeeId", UUID.randomUUID().toString()))
                                .andExpect(status().isNotFound());

                verify(productApplicationService, times(1)).getModifiersByBusinessId(0, 10, businessId);
        }

        @Test
        void shouldUpdateModifierSuccessfully() throws Exception {
                // Arrange
                ProductModifierDTO responseModifierDTO = ProductModifierDTO.builder()
                                .id(modifierId)
                                .title("Updated Modifier")
                                .quantityInStock(200)
                                .price(validPutModifierDTO.getPrice())
                                .businessId(businessId)
                                .build();

                when(productApplicationService.updateProductModifier(any(PutModifierDTO.class), eq(modifierId)))
                                .thenReturn(responseModifierDTO);

                // Act & Assert
                mockMvc.perform(put("/v1/products/modifiers/{modifierId}", modifierId)
                                .param("employeeId", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validPutModifierDTO)))
                                .andExpect(status().isOk());

                verify(productApplicationService, times(1)).updateProductModifier(any(PutModifierDTO.class),
                                eq(modifierId));
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingNonexistentModifier() throws Exception {
                // Arrange
                doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                                .when(productApplicationService)
                                .updateProductModifier(any(PutModifierDTO.class), eq(modifierId));

                // Act & Assert
                mockMvc.perform(put("/v1/products/modifiers/{modifierId}", modifierId)
                                .param("employeeId", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validPutModifierDTO)))
                                .andExpect(status().isNotFound());

                verify(productApplicationService, times(1)).updateProductModifier(any(PutModifierDTO.class),
                                eq(modifierId));
        }

        @Test
        void shouldDeleteModifierSuccessfully() throws Exception {
                // Act & Assert
                mockMvc.perform(delete("/v1/products/modifiers/{modifierId}", modifierId)
                                .param("employeeId", UUID.randomUUID().toString()))
                                .andExpect(status().isNoContent());

                verify(productApplicationService, times(1)).deleteProductModifier(modifierId);
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonexistentModifier() throws Exception {
                // Arrange
                doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                                .when(productApplicationService).deleteProductModifier(eq(modifierId));

                // Act & Assert
                mockMvc.perform(delete("/v1/products/modifiers/{modifierId}", modifierId)
                                .param("employeeId", UUID.randomUUID().toString()))
                                .andExpect(status().isNotFound());

                verify(productApplicationService, times(1)).deleteProductModifier(eq(modifierId));
        }
}
