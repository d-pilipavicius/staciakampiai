package com.example.demo.serviceChargeComponent.repository;

import com.example.demo.serviceChargeComponent.domain.entities.ServiceCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceChargeRepository extends JpaRepository<ServiceCharge, UUID> {

}
