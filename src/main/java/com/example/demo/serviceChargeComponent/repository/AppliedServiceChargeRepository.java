package com.example.demo.serviceChargeComponent.repository;

import com.example.demo.serviceChargeComponent.domain.entities.AppliedServiceCharge;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface AppliedServiceChargeRepository extends JpaRepository<AppliedServiceCharge, UUID> {

}
