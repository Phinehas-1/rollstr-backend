package com.bigdecimal.springbootbackend4nextjs.roster.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRosterPayload(
  @NotBlank(message = "Please provide start date.") String startDate,
  @NotBlank(message = "Please provide end date.") String endDate,
  @NotBlank(message = "Please provide cateogry.") String category
) {}
