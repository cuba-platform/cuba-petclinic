package com.cubaplatform.petclinic.entity.vet;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.cubaplatform.petclinic.entity.NamedEntity;

@Table(name = "PETCLINIC_SPECIALTY")
@Entity(name = "petclinic_Specialty")
public class Specialty extends NamedEntity {
    private static final long serialVersionUID = -1397207634482158673L;

}