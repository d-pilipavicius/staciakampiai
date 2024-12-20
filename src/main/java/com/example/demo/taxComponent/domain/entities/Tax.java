package com.example.demo.taxComponent.domain.entities;

//import com.example.demo.productComponent.domain.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter 
@Setter
@Table(
        name = "tax",
        indexes = {
                @Index(name = "idx_tax_business_id", columnList = "business_id")
        }
)
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @ElementCollection
    @CollectionTable(name = "product_tax", joinColumns = @JoinColumn(name = "tax_id"))
    @Column(name = "product_id"/*, nullable = false*/)
    private List<UUID> productIds;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "rate_percentage", nullable = false, precision = 10, scale = 2)
    private BigDecimal ratePercentage;
}
