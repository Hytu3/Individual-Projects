package com.findfishaz.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;



@Entity
public class WaterBody
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;

  public String name;

  public String city;

  public String type;

  public Boolean isPrivate;

  // Constructor
  public WaterBody(){}


  // Getters and Setters
  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public Boolean getIsPrivate() 
  {
    return isPrivate;
  }

  public void setIsPrivate(Boolean isPrivate) 
  {
    this.isPrivate = isPrivate;
  }

  // One to many relationship with fishes
  @ManyToMany(mappedBy = "waterBodies")
  private List<Fish> fishes = new ArrayList<>(); 

  // Your Getter
  public List<Fish> getFishes() 
  {
      return fishes;
  }

  // Your Setter
  public void setFishes(List<Fish> fishes) 
  {
      this.fishes = fishes;
  }


}