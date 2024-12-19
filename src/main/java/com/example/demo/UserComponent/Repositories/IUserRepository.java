package com.example.demo.UserComponent.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.UserComponent.Domain.Entities.User;

public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    List<User> findAllByBusiness(Business business);
 
    int countByUsername(String username);
    Boolean existsByUsername(String username);
}