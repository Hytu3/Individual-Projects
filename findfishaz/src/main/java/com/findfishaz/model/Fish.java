package com.findfishaz.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



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

// Many to one relationship
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "waterbody_id") // This creates the Foreign Key column in MySQL
@JsonBackReference                // Prevents infinite loops when sending data to a browser
private WaterBody waterBody;


public WaterBody getWaterBody() 
{
    return waterBody;
}


public void setWaterBody(WaterBody waterBody) 
{
    this.waterBody = waterBody;
}


}

