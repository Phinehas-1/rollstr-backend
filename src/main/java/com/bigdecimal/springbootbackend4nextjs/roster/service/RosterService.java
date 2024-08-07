package com.bigdecimal.springbootbackend4nextjs.roster.service;

import com.bigdecimal.springbootbackend4nextjs.part.model.AssignmentCategory;
import com.bigdecimal.springbootbackend4nextjs.roster.data.RosterRepository;
import com.bigdecimal.springbootbackend4nextjs.roster.dto.CreateRosterPayload;
import com.bigdecimal.springbootbackend4nextjs.roster.model.Roster;
import com.bigdecimal.springbootbackend4nextjs.roster.model.RosterStatus;
import com.bigdecimal.springbootbackend4nextjs.shared.exception.InvalidOperationException;

import java.sql.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RosterService {

  private final RosterRepository rosterRepository;

  public void addRoster(CreateRosterPayload payload) {
    Boolean openRosterExists = rosterRepository
      .findAll()
      .stream()
      .anyMatch(roster ->
        roster.getStatus().equals(RosterStatus.OPEN) &&
        AssignmentCategory.valueOf(payload.category()).equals(roster.getAssignmentCategory())
      );
    if (openRosterExists) {
      throw new InvalidOperationException(
        "Failed to create a new roster because there is still an open roster."
      );
    }
    Roster roster = rosterRepository.save(
      new Roster(
        Date.valueOf(payload.startDate()),
        Date.valueOf(payload.endDate()),
        AssignmentCategory.valueOf(payload.category()),
        RosterStatus.OPEN
      )
    );
    log.info("Roster id {} created.", roster.getId());
  }
}
