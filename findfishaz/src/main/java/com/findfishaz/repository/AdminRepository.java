package com.findfishaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.findfishaz.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> 
{
    // Basic CRUD functions automatically usable now
}