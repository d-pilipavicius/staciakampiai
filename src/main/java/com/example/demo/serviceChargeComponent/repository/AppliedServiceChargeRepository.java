package com.example.demo.serviceChargeComponent.repository;

import com.example.demo.serviceChargeComponent.domain.entities.AppliedServiceCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppliedServiceChargeRepository extends JpaRepository<AppliedServiceCharge, UUID> {

}
