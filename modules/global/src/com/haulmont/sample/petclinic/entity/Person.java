package com.haulmont.sample.petclinic.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@NamePattern("#getName|firstName,lastName")
@MappedSuperclass
public class Person extends StandardEntity {
    private static final long serialVersionUID = -2777766826323269523L;

    @NotNull
    @Column(name = "FIRST_NAME", nullable = false)
    protected String firstName;

    @Column(name = "LAST_NAME")
    protected String lastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    @Transient
    @MetaProperty(related = "firstName,lastName")
    public String getName() {
        String name = firstName;

        if (lastName != null) {
            name += " " + lastName;
        }

        return name;
    }
}