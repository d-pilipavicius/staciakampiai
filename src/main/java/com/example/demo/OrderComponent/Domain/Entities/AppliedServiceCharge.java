package com.example.demo.OrderComponent.Domain.Entities;

//import com.example.demo.orderComponent.domain.entities.Order;
import com.example.demo.CommonHelper.enums.Currency;
//import com.example.demo.reservationComponent.domain.entities.Reservation;
import com.example.demo.CommonHelper.enums.PricingStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applied_service_charge"/*, indexes = {
        @Index(name = "idx_applied_service_charge_charged_by_employee_id", columnList = "charged_by_employee_id"),
        @Index(name = "idx_applied_service_charge_order_id", columnList = "order_id"),
        @Index(name = "idx_applied_service_charge_reservation_id", columnList = "reservation_id")
}*/)
public class AppliedServiceCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * @ManyToOne
     * 
     * @JoinColumn(name = "charged_by_employee_id", nullable = false)
     * private Employee employee;
     * 
     * @ManyToOne
     * 
     * @JoinColumn(name = "order_id", nullable = true)
     * private Order order;
     * 
     * @ManyToOne
     * 
     * @JoinColumn(name = "reservation_id", nullable = true)
     * private Reservation reservation;
     */

    @Column(name = "charged_by_employee_id", nullable = false)
    private UUID employeeId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @Column(name = "reservation_id", nullable = true)
    private UUID reservation;

    @Column(nullable = false)
    private String title;

    @Column(name = "service_charge_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(name = "value_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PricingStrategy valueType;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
