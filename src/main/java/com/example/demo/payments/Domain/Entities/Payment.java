package com.example.demo.payments.Domain.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.demo.payments.Domain.Entities.Enums.Currency;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Entities.Enums.PaymentStatus;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod method;

    @Column(name = "transaction_id")
    private String transactionId; // Nullable for cash

    @Column(name = "payment_processor_id")
    private String paymentProcessorId; // Nullable for cash

}
