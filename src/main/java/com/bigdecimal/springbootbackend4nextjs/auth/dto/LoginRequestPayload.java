package com.bigdecimal.springbootbackend4nextjs.auth.dto;

public record LoginRequestPayload(String username, String password) {
  public LoginRequestPayload(String username, String password) {
    this.username = username;
    this.password = password;
    if (username.isBlank() || password.isBlank()) {
      throw new IllegalArgumentException("Username and password is required.");
    }
  }
}
