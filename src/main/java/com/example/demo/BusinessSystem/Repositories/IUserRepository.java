package com.example.demo.BusinessSystem.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.BusinessSystem.Entities.User;

public interface IUserRepository extends JpaRepository<User, UUID> {
}
