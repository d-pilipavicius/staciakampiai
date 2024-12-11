package com.example.demo.discountComponent.domain.entities;


import com.example.demo.helper.enums.Currency;
import com.example.demo.helper.enums.DiscountTarget;
import com.example.demo.helper.enums.PricingStrategy;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(
        name = "discount"/*,
        indexes = {
                @Index(name = "idx_discount_business_id", columnList = "business_id"),
                @Index(name = "idx_discount_applied_discount_id", columnList = "applied_discount_id"),
        }*/
)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @ElementCollection
    @CollectionTable(name = "discount_entitled_product", joinColumns = @JoinColumn(name = "discount_id"))
    @Column(name = "product_id")
    private List<UUID> productIds;

    @Column(nullable = true, name = "code")
    private String code;

    @Column(nullable = false, precision = 5, scale = 2, name = "amount")
    private BigDecimal amount;

    @Column(nullable = false, name = "value_type")
    @Enumerated(EnumType.STRING)
    private PricingStrategy valueType;

    @Column(nullable = true, name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false, name = "target")
    @Enumerated(EnumType.STRING)
    private DiscountTarget target;

    @Column(nullable = false, name = "valid_from")
    private Timestamp validFrom;

    @Column(nullable = false, name = "valid_until")
    private Timestamp validUntil;

    @Column(nullable = true, name = "usage_count_limit")
    private Integer usageCountLimit;
}
