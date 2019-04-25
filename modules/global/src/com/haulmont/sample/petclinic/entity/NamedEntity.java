package com.haulmont.sample.petclinic.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@MappedSuperclass
public class NamedEntity extends StandardEntity {
    private static final long serialVersionUID = -629159912292308518L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}