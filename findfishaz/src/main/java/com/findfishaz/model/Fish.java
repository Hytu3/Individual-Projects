package com.findfishaz.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import java.util.List;
import java.util.ArrayList;



@Entity
public class Fish
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String species;

    public String info;

    // Constructor
    public Fish(){}


    // Getters and Setters
    public Integer getId()
    {
      return id;
    }

    public void setId(Integer id)
    {
      this.id = id;
    }

    public String getInfo()
    {
      return info;
    }

    public void setInfo(String info)
    {
      this.info = info;
    }

    public String getSpecies()
    {
      return species;
    }

    public void setSpecies(String species)
    {
      this.species = species;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    (
      name = "fish_waterbody",
      joinColumns = @JoinColumn(name = "fish_id"),
      inverseJoinColumns = @JoinColumn(name = "waterbody_id")
    )

    private List<WaterBody> waterBodies = new ArrayList<>();


    public List<WaterBody> getWaterBodies() 
    {
      return waterBodies;
    }


    public void setWaterBodies(List<WaterBody> waterBodies) 
    {
      this.waterBodies = waterBodies;
    }


}

