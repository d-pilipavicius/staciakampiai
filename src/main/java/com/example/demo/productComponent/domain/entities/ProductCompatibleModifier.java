package com.example.demo.productComponent.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(
        name = "product_compatible_modifier"
)
@IdClass(ProductCompatibleModifier.class)
public class ProductCompatibleModifier {
    @Id
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Id
    @Column(name = "modifier_id", nullable = false)
    private UUID modifierId;
}
