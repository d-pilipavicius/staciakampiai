package com.example.demo.payments.Domain.Entities;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_processor")
public class PaymentProcessor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;
}
