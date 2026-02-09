package com.findfishaz.repository;

import com.findfishaz.model.WaterBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterBodyRepository extends JpaRepository<WaterBody, Integer> 
{
  // Basic CRUD functions automatically usable now
}