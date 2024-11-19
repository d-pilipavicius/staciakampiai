package com.example.demo.orders.domain.entities;

import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.DiscountType;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "order_item_id", nullable = true)
    private OrderItem orderItem;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = true)
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "charged_by_employee_id", nullable = false)
    private EmployeeMock employee;

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
