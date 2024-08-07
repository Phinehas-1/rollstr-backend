package com.bigdecimal.springbootbackend4nextjs.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class AppUser implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String username;

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  public AppUser(String username, String password, UserRole role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
