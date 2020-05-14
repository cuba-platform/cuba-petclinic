package com.haulmont.sample.petclinic.entity.veterinarian;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.sample.petclinic.entity.NamedEntity;

@Table(name = "PETCLINIC_SPECIALTY")
@Entity(name = "petclinic_Specialty")
public class Specialty extends NamedEntity {
    private static final long serialVersionUID = -1397207634482158673L;

}