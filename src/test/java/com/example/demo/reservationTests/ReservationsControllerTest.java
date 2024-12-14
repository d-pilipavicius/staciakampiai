package com.example.demo.reservationTests;

import com.example.demo.reservationComponent.api.dtos.GetReservationsDTO;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.PutReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.CustomerDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.api.endpoints.ReservationsController;
import com.example.demo.reservationComponent.applicationServices.ReservationApplicationService;
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

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ReservationsController.class)
@AutoConfigureMockMvc
class ReservationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationApplicationService reservationApplicationService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostReservationDTO validPostReservationDTO;
    private PutReservationDTO validPutReservationDTO;
    private UUID reservationId;
    private UUID businessId;
    private UUID employeeId;

    @BeforeEach
    void setup() {
        employeeId = UUID.randomUUID();
        businessId = UUID.randomUUID();
        reservationId = UUID.randomUUID();

        validPostReservationDTO = PostReservationDTO.builder()
                .customer(CustomerDTO.builder().firstName("John").lastName("Bush").phoneNumber("+370111111111111").build())
                .reservationStartAt(Timestamp.valueOf("2024-12-01 10:00:00"))
                .reservationEndAt(Timestamp.valueOf("2024-12-01 11:00:00"))
                .serviceChargeIds(Collections.singletonList(UUID.randomUUID()))
                .businessId(businessId)
                .build();

        validPutReservationDTO = PutReservationDTO.builder()
                .customer(Optional.ofNullable(CustomerDTO.builder().firstName("John").lastName("Bush").phoneNumber("+370111111111111").build()))
                .reservationStartAt(Optional.of(Timestamp.valueOf("2024-12-01 12:00:00")))
                .reservationEndAt(Optional.of(Timestamp.valueOf("2024-12-01 13:00:00")))
                .build();
    }

    // POST: Create Reservation
    @Test
    void shouldCreateReservationSuccessfully() throws Exception {
        // Arrange
        ReservationDTO reservationDTO = ReservationDTO.builder()
                .id(reservationId)
                .customer(validPostReservationDTO.getCustomer())
                .reservationStartAt(validPostReservationDTO.getReservationStartAt())
                .reservationEndAt(validPostReservationDTO.getReservationEndAt())
                .build();

        when(reservationApplicationService.createReservation(any(PostReservationDTO.class), eq(employeeId)))
                .thenReturn(reservationDTO);

        // Act & Assert
        mockMvc.perform(post("/v1/reservations")
                        .param("employeeId", employeeId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPostReservationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(reservationId.toString()))
                .andExpect(jsonPath("$.customer.name").value("John Doe"));

        verify(reservationApplicationService, times(1))
                .createReservation(any(PostReservationDTO.class), eq(employeeId));
    }

    @Test
    void shouldReturnNotFoundWhenCreatingReservationFails() throws Exception {
        // Arrange
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(reservationApplicationService)
                .createReservation(any(PostReservationDTO.class), eq(employeeId));

        // Act & Assert
        mockMvc.perform(post("/v1/reservations")
                        .param("employeeId", employeeId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPostReservationDTO)))
                .andExpect(status().isNotFound());

        verify(reservationApplicationService, times(1))
                .createReservation(any(PostReservationDTO.class), eq(employeeId));
    }

    // GET: Fetch Reservations
    @Test
    void shouldFetchReservationsSuccessfully() throws Exception {
        // Arrange
        GetReservationsDTO reservationsDTO = GetReservationsDTO.builder()
                .items(Collections.singletonList(ReservationDTO.builder()
                        .id(reservationId)
                        .customer(validPostReservationDTO.getCustomer())
                        .reservationStartAt(validPostReservationDTO.getReservationStartAt())
                        .reservationEndAt(validPostReservationDTO.getReservationEndAt())
                        .build()))
                .build();

        when(reservationApplicationService.getReservationsByBusinessId(eq(businessId), anyInt(), anyInt()))
                .thenReturn(reservationsDTO);

        // Act & Assert
        mockMvc.perform(get("/v1/reservations")
                        .param("businessId", businessId.toString())
                        .param("employeeId", employeeId.toString())
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(reservationId.toString()));

        verify(reservationApplicationService, times(1))
                .getReservationsByBusinessId(eq(businessId), anyInt(), anyInt());
    }

    @Test
    void shouldReturnNotFoundWhenFetchingReservationsFails() throws Exception {
        // Arrange
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(reservationApplicationService)
                .getReservationsByBusinessId(eq(businessId), anyInt(), anyInt());

        // Act & Assert
        mockMvc.perform(get("/v1/reservations")
                        .param("businessId", businessId.toString())
                        .param("employeeId", employeeId.toString())
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isNotFound());

        verify(reservationApplicationService, times(1))
                .getReservationsByBusinessId(eq(businessId), anyInt(), anyInt());
    }

    // PUT: Update Reservation
    @Test
    void shouldUpdateReservationSuccessfully() throws Exception {
        // Arrange
        ReservationDTO updatedReservation = ReservationDTO.builder()
                .id(reservationId)
                .customer(validPutReservationDTO.getCustomer().orElse(null))
                .reservationStartAt(validPutReservationDTO.getReservationStartAt().orElse(null))
                .reservationEndAt(validPutReservationDTO.getReservationEndAt().orElse(null))
                .build();

        when(reservationApplicationService.updateReservation(any(PutReservationDTO.class), eq(reservationId)))
                .thenReturn(updatedReservation);

        // Act & Assert
        mockMvc.perform(put("/v1/reservations/{reservationId}", reservationId)
                        .param("employeeId", employeeId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPutReservationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservationId.toString()))
                .andExpect(jsonPath("$.customer.name").value("Jane Doe"));

        verify(reservationApplicationService, times(1))
                .updateReservation(any(PutReservationDTO.class), eq(reservationId));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingReservationFails() throws Exception {
        // Arrange
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(reservationApplicationService)
                .updateReservation(any(PutReservationDTO.class), eq(reservationId));

        // Act & Assert
        mockMvc.perform(put("/v1/reservations/{reservationId}", reservationId)
                        .param("employeeId", employeeId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPutReservationDTO)))
                .andExpect(status().isNotFound());

        verify(reservationApplicationService, times(1))
                .updateReservation(any(PutReservationDTO.class), eq(reservationId));
    }

    // DELETE: Delete Reservation
    @Test
    void shouldDeleteReservationSuccessfully() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/v1/reservations/{reservationId}", reservationId)
                        .param("employeeId", employeeId.toString()))
                .andExpect(status().isNoContent());

        verify(reservationApplicationService, times(1))
                .deleteReservation(eq(reservationId));
    }

    @Test
    void shouldReturnNotFoundWhenDeletingReservationFails() throws Exception {
        // Arrange
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(reservationApplicationService)
                .deleteReservation(eq(reservationId));

        // Act & Assert
        mockMvc.perform(delete("/v1/reservations/{reservationId}", reservationId)
                        .param("employeeId", employeeId.toString()))
                .andExpect(status().isNotFound());

        verify(reservationApplicationService, times(1))
                .deleteReservation(eq(reservationId));
    }
}
