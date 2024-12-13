package com.example.demo.OrderComponent.Repositories;

import com.example.demo.OrderComponent.Domain.Entities.AppliedServiceCharge;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface IAppliedServiceChargeRepository extends JpaRepository<AppliedServiceCharge, UUID> {

    List<AppliedServiceCharge> findByOrderId(UUID id);
}
