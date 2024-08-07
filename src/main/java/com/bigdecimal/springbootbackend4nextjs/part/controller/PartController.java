package com.bigdecimal.springbootbackend4nextjs.part.controller;

import com.bigdecimal.springbootbackend4nextjs.part.dto.CreateAssignmentPayload;
import com.bigdecimal.springbootbackend4nextjs.part.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class PartController {

  private final PartService assignmentService;

  @PostMapping
  public ResponseEntity<?> postMethodName(
    @RequestBody CreateAssignmentPayload payload
  ) {
    assignmentService.addPublicTalkAssignment(payload);
    return ResponseEntity.ok().body(null);
  }
}
