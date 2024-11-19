package com.example.demo.orders.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        name = "applied_service_charge",
        indexes = {
                @Index(name = "idx_applied_service_charge_charged_by_employee_id", columnList = "charged_by_employee_id"),
                @Index(name = "idx_applied_service_charge_order_id", columnList = "order_id"),
                @Index(name = "idx_applied_service_charge_reservation_id", columnList = "reservation_id")
        }
)
public class BusinessMock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
