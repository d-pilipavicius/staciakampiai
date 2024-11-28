package com.example.demo.taxComponent.repository;

import com.example.demo.taxComponent.domain.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaxRepository extends JpaRepository<Tax, UUID> {

}
