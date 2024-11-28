package com.example.demo.discountComponent.domain.entities;

import com.example.demo.orderComponent.domain.entities.Order;
import com.example.demo.orderComponent.domain.entities.OrderItem;
import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.DiscountType;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
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
        name = "applied_discount",
        indexes = {
                // TODO: Use composite indexes if applicable
                @Index(name = "idx_applied_discounts_order_id", columnList = "order_id"),
                @Index(name = "idx_applied_discounts_order_item_id", columnList = "order_item_id"),
                @Index(name = "idx_applied_discounts_discount_id", columnList = "discount_id"),
                @Index(name = "idx_applied_discounts_charged_by_employee_id", columnList = "charged_by_employee_id"),
        }
)
public class AppliedDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JoinColumn(name = "order_id", nullable = false)
    private UUID orderId;

    @JoinColumn(name = "order_item_id", nullable = true)
    private UUID orderItemId;

    @JoinColumn(name = "discount_id", nullable = true)
    private UUID discountId;

    @JoinColumn(name = "charged_by_employee_id", nullable = false)
    private UUID employeeId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PricingStrategy valueType;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
