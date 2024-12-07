package com.example.demo.BusinessComponent.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.BusinessComponent.Domain.Entities.User;

public interface IUserRepository extends JpaRepository<User, UUID> {
}
