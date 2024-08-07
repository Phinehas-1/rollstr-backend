package com.bigdecimal.springbootbackend4nextjs.user.dto;

import com.bigdecimal.springbootbackend4nextjs.user.model.UserRole;
import java.util.List;

public record UserRegistrationRequestPayload(
  String username,
  String password,
  String role
) {
  public UserRegistrationRequestPayload(
    String username,
    String password,
    String role
  ) {
    this.username = username;
    this.password = password;
    this.role = role.toUpperCase();
    System.out.println(this.role);
    if (username.isBlank() || password.isBlank()) {
      throw new IllegalArgumentException("Username and password is required.");
    }
    if (role.isBlank()) {
      throw new IllegalArgumentException("User must be assigned a role.");
    }
    if (
      !List
        .of(UserRole.ADMIN.name(), UserRole.PARTICIPANT.name())
        .contains(this.role)
    ) {
      throw new IllegalArgumentException("User role is invalid.");
    }
  }
}
