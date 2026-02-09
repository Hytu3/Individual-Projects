package com.findfishaz.repository;

import com.findfishaz.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<Fish, Integer> 
{
    // Basic CRUD functions automatically usable now
}