package com.example.demo.orders.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @ManyToMany(mappedBy = "taxes")
    private List<Product> products;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ratePercentage;
}
