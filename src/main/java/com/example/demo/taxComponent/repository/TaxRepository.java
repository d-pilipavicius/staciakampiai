package com.example.demo.taxComponent.repository;

import com.example.demo.taxComponent.domain.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface TaxRepository extends JpaRepository<Tax, UUID> {

}
