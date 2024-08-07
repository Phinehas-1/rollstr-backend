package com.bigdecimal.springbootbackend4nextjs.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigdecimal.springbootbackend4nextjs.user.dto.UserRegistrationRequestPayload;
import com.bigdecimal.springbootbackend4nextjs.user.service.UserRegistrationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRegistrationController {

  private final UserRegistrationService userRegistrationService;

  @PostMapping
  public ResponseEntity<?> addUser(
    @RequestBody UserRegistrationRequestPayload payload
  ) {
    return ResponseEntity
      .ok()
      .body(userRegistrationService.addAppUser(payload));
  }
}
