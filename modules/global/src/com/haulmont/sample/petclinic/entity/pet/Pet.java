package com.haulmont.sample.petclinic.entity.pet;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.sample.petclinic.entity.NamedEntity;
import com.haulmont.sample.petclinic.entity.owner.Owner;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamePattern("%s - %s|identificationNumber,name")
@Table(name = "PETCLINIC_PET", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_PETCLINIC_PET_ID_UNQ", columnNames = {"IDENTIFICATION_NUMBER", "DELETE_TS"})
})
@Entity(name = "petclinic_Pet")
public class Pet extends NamedEntity {
    private static final long serialVersionUID = -3431453457784831240L;

    @NotNull
    @Column(name = "IDENTIFICATION_NUMBER", nullable = false)
    protected String identificationNumber;

    @Column(name = "BIRTH_DATE")
    protected LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    protected PetType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    protected Owner owner;

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public PetType getType() {
        return type;
    }

}