package com.bigdecimal.springbootbackend4nextjs.roster.data;

import com.bigdecimal.springbootbackend4nextjs.roster.model.Roster;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RosterRepository extends JpaRepository<Roster, UUID> {}
