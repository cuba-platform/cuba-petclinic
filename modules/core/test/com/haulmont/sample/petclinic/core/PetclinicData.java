package com.haulmont.sample.petclinic.core;

import com.haulmont.cuba.security.entity.User;
import com.haulmont.sample.petclinic.entity.owner.Owner;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.pet.PetType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * PetclinicData represents an API abstraction for creating different entities for test purposes
 */
public class PetclinicData {


  public Pet pet(String name) {
    Pet pet = new Pet();
    pet.setName(name);
    return pet;
  }

  public PetType electricType() {
    PetType type = new PetType();
    type.setName("Electric");
    return type;
  }
  public PetType waterType() {
    PetType type = new PetType();
    type.setName("Water");
    return type;
  }

  public PetType fireType() {
    PetType type = new PetType();
    type.setName("Fire");
    return type;
  }

  public User nurse(String name) {
    User nurse = new User();
    nurse.setName(name);
    return nurse;
  }
}