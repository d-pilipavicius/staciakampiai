package com.example.demo.UserComponent.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.UserComponent.Domain.Entities.User;

public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    int countByUsername(String username);
    Boolean existsByUsername(String username);
}
