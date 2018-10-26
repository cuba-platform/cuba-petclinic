package com.cubaplatform.petclinic.entity.owner;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.cubaplatform.petclinic.entity.Person;
import com.cubaplatform.petclinic.entity.pet.Pet;
import java.util.List;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.Email;

@Table(name = "PETCLINIC_OWNER")
@Entity(name = "petclinic_Owner")
public class Owner extends Person {
    private static final long serialVersionUID = 901690119511259222L;

    @NotNull
    @Column(name = "ADDRESS", nullable = false)
    protected String address;

    @NotNull
    @Column(name = "CITY", nullable = false)
    protected String city;

    @Email
    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "TELEPHONE")
    protected String telephone;

    @OneToMany(mappedBy = "owner")
    protected List<Pet> pets;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Pet> getPets() {
        return pets;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }


}