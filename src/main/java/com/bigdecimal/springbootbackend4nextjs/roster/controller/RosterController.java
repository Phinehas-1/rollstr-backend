package com.bigdecimal.springbootbackend4nextjs.roster.controller;

import com.bigdecimal.springbootbackend4nextjs.roster.dto.CreateRosterPayload;
import com.bigdecimal.springbootbackend4nextjs.roster.service.RosterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rosters")
@RequiredArgsConstructor
@Slf4j
public class RosterController {

  private final RosterService rosterService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createNewRoster(
    @Valid @RequestBody CreateRosterPayload payload, Authentication authentication
  ) {
    log.info("User name is: {}", authentication.getName());
    rosterService.addRoster(payload);
    return ResponseEntity.ok().build();
  }
}
