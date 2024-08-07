package com.bigdecimal.springbootbackend4nextjs.user.service;

import com.bigdecimal.springbootbackend4nextjs.user.data.AppUserRepository;
import com.bigdecimal.springbootbackend4nextjs.user.dto.UserRegistrationRequestPayload;
import com.bigdecimal.springbootbackend4nextjs.user.model.AppUser;
import com.bigdecimal.springbootbackend4nextjs.user.model.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationService {

  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder;

  public AppUser addAppUser(UserRegistrationRequestPayload payload) {
    AppUser user = appUserRepository.save(
      new AppUser(
        payload.username(),
        passwordEncoder.encode(payload.password()),
        UserRole.valueOf(payload.role())
      )
    );
    log.info("Saved new username: {}", user.getUsername());
    return user;
  }
}
