package com.haulmont.sample.petclinic.entity.pet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.sample.petclinic.entity.NamedEntity;

@Table(name = "PETCLINIC_PET_TYPE")
@Entity(name = "petclinic_PetType")
public class PetType extends NamedEntity {
    private static final long serialVersionUID = -2633909809493220411L;

    @Column(name = "COLOR")
    protected String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}