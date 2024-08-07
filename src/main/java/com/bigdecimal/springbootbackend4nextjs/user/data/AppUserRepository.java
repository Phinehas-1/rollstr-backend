package com.bigdecimal.springbootbackend4nextjs.user.data;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigdecimal.springbootbackend4nextjs.user.model.AppUser;


public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
}
