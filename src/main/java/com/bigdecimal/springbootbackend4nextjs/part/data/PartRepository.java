package com.bigdecimal.springbootbackend4nextjs.part.data;

import com.bigdecimal.springbootbackend4nextjs.part.model.Part;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, UUID> {}
