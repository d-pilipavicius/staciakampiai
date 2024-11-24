package com.example.demo.BusinessSystem.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.BusinessSystem.Entities.Business;

public interface IBusinessRepository extends JpaRepository<Business, UUID> {
}
