package com.example.demo.BusinessComponent.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.BusinessComponent.Domain.Entities.Business;

public interface IBusinessRepository extends JpaRepository<Business, UUID> {
}
