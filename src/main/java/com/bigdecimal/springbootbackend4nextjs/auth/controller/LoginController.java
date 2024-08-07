package com.bigdecimal.springbootbackend4nextjs.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigdecimal.springbootbackend4nextjs.auth.dto.LoginRequestPayload;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {


  @PostMapping("/signin")
  public ResponseEntity<?> login(@RequestBody LoginRequestPayload payload) {
    return ResponseEntity.ok().build();
  }
}
