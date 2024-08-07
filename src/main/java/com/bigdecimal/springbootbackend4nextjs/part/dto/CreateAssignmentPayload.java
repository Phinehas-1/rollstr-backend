package com.bigdecimal.springbootbackend4nextjs.part.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAssignmentPayload(
  @NotBlank String topic,
  @NotBlank Integer assignmentNumber
) {}
