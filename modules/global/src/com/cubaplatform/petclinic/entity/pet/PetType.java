package com.cubaplatform.petclinic.entity.pet;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.cubaplatform.petclinic.entity.NamedEntity;

@Table(name = "PETCLINIC_PET_TYPE")
@Entity(name = "petclinic_PetType")
public class PetType extends NamedEntity {
    private static final long serialVersionUID = -2633909809493220411L;

}