package com.bigdecimal.springbootbackend4nextjs.user.service;

import com.bigdecimal.springbootbackend4nextjs.auth.model.AuthorizationScope;
import com.bigdecimal.springbootbackend4nextjs.user.data.AppUserRepository;
import com.bigdecimal.springbootbackend4nextjs.user.model.AppUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManagementService {

  private final AppUserRepository appUserRepository;

  @Cacheable("user")
  public AppUser findUserByUsername(
    String username
  ) {
    log.debug("Fetching username {} from database", username);
    return appUserRepository.findByUsername(username).orElseThrow();
  }

  @Cacheable("user")
  public List<AppUser> getAllUsers() {
    return appUserRepository.findAll();
  }

  @Cacheable("user")
  public List<AppUser> findUsersByUsernames(List<String> usernames) {
    throw new UnsupportedOperationException("Unimplemented method");
  }

  @CacheEvict("user")
  public AppUser deleteUserByUsername(String username) {
    AppUser user = appUserRepository.findByUsername(username).orElseThrow();
    appUserRepository.delete(user);
    return user;
  }

  private void grantThirdPartyAuthorityByScope(
    String username,
    Authentication authentication,
    AuthorizationScope scope
  ) {
    List<String> authorities = authentication
      .getAuthorities()
      .stream()
      .map(grantedAuthority -> grantedAuthority.getAuthority())
      .toList();
    if (!authorities.contains(scope.name())) {
      if (!authentication.getName().equals(username)) {
        log.error(
          "Error while fetching user {} by username {}",
          authentication.getName(),
          username
        );
        throw new UnsupportedOperationException("Can't fetch user details");
      }
    }
  }
}
