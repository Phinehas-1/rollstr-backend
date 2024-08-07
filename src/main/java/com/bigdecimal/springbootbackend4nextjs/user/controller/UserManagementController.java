package com.bigdecimal.springbootbackend4nextjs.user.controller;

import com.bigdecimal.springbootbackend4nextjs.shared.dto.ApiResponse;
import com.bigdecimal.springbootbackend4nextjs.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserManagementController {

  private final UserManagementService userManagementService;

  @GetMapping
  public ResponseEntity<?> getUsers() {
    throw new UnsupportedOperationException("Unimplemented method.");
  }
  
  @GetMapping("/{username}")
  public ResponseEntity<?> findUser(
    @PathVariable String username,
    Authentication authentication
  ) {
    return ResponseEntity
      .ok()
      .body(
        new ApiResponse(
          true,
          userManagementService.findUserByUsername(username),
          null
        )
      );
  }

  @Secured(value = "SCOPE_ADMIN")
  @DeleteMapping("/{username}")
  public ResponseEntity<?> deleteUser(
    @PathVariable String username,
    Authentication authentication
  ) {
    return ResponseEntity
      .ok()
      .body(
        new ApiResponse(
          true,
          userManagementService.deleteUserByUsername(username),
          null
        )
      );
  }
}
