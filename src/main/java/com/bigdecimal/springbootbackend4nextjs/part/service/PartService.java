package com.bigdecimal.springbootbackend4nextjs.part.service;

import com.bigdecimal.springbootbackend4nextjs.part.data.PartRepository;
import com.bigdecimal.springbootbackend4nextjs.part.dto.CreateAssignmentPayload;
import com.bigdecimal.springbootbackend4nextjs.part.model.Part;
import com.bigdecimal.springbootbackend4nextjs.shared.exception.InvalidOperationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartService {

  private final PartRepository assignmentRepository;

  public void addPublicTalkAssignment(CreateAssignmentPayload payload) {
    Boolean assignmentExists = assignmentRepository
      .findAll()
      .stream()
      .anyMatch(assignment ->
        assignment.getAssignmentNumber().equals(payload.assignmentNumber())
      );
    if (assignmentExists) {
      throw new InvalidOperationException(
        "Failed to create a new assignment because it already exists."
      );
    }
    Part assignment = assignmentRepository.save(
      new Part(payload.topic(), payload.assignmentNumber(), null)
    );
    log.info("Assignement id {} created.", assignment.getId());
  }
}
