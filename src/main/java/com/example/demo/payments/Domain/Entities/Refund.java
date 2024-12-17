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
import com.example.demo.payments.Domain.Entities.Enums.PaymentRefundStatus;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentRefundStatus status;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "payment_processor_id")
    private String paymentProcessorId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
