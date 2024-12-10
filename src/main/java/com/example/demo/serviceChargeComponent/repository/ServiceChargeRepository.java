package com.example.demo.serviceChargeComponent.repository;

import com.example.demo.serviceChargeComponent.domain.entities.ServiceCharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceChargeRepository extends JpaRepository<ServiceCharge, UUID> {

}
