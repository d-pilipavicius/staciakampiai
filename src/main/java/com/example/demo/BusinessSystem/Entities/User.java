package com.example.demo.BusinessSystem.Entities;

import java.util.UUID;

import com.example.demo.BusinessSystem.Entities.Enums.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @OneToOne
  @JoinColumn(name = "business_id", referencedColumnName = "id", nullable = false)
  private Business business;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private RoleType role;

  @Pattern(regexp = "\\+\\d{3,30}")
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;
  
  @Email
  @Column(name = "email_address", nullable = false)
  private String emailAddress;
}
