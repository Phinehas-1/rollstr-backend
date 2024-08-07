package com.bigdecimal.springbootbackend4nextjs.roster.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.UUID;

import com.bigdecimal.springbootbackend4nextjs.part.model.AssignmentCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Roster {

  public Roster(
    Date startDate,
    Date endDate,
    AssignmentCategory assignmentCategory,
    RosterStatus status
  ) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.assignmentCategory = assignmentCategory;
    this.status = status;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "roster_id")
  private UUID id;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @Column(name = "assignment_category")
  @Enumerated(EnumType.STRING)
  private AssignmentCategory assignmentCategory;

  @Enumerated(EnumType.STRING)
  private RosterStatus status;
}
