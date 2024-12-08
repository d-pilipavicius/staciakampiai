package com.example.demo.serviceChargeComponent.domain.entities;

//import com.example.demo.orderComponent.domain.entities.Order;
import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
//import com.example.demo.reservationComponent.domain.entities.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
public class AppliedServiceCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

   /* @ManyToOne
    @JoinColumn(name = "charged_by_employee_id", nullable = false)
    private Employee employee; 

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = true)
    private Reservation reservation; */

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PricingStrategy valueType;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
