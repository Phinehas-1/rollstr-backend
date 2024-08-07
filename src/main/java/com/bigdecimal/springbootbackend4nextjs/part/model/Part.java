package com.bigdecimal.springbootbackend4nextjs.part.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Part {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String topic;
  private Integer assignmentNumber;
  private AssignmentCategory assignmentCategory;

  public Part(String topic, Integer assignmentNumber, AssignmentCategory assignmentCategory) {
    this.topic = topic;
    this.assignmentNumber = assignmentNumber;
    this.assignmentCategory = assignmentCategory;
  }
}
