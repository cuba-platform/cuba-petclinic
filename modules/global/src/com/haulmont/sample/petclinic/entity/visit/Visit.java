package com.haulmont.sample.petclinic.entity.visit;

import static com.haulmont.sample.petclinic.entity.visit.VisitTreatmentStatus.*;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.sample.petclinic.entity.NamedEntity;
import com.haulmont.sample.petclinic.entity.pet.Pet;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@NamePattern("%s ()|pet")
@Table(name = "PETCLINIC_VISIT")
@Entity(name = "petclinic_Visit")
public class Visit extends StandardEntity {
    private static final long serialVersionUID = 6351202390461847589L;

    @NotNull
    @Column(name = "TYPE_", nullable = false)
    protected String type;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNED_NURSE_ID")
    protected User assignedNurse;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PET_ID")
    protected Pet pet;

    @NotNull
    @Column(name = "VISIT_START", nullable = false)
    protected LocalDateTime visitStart;

    @NotNull
    @Column(name = "VISIT_END", nullable = false)
    protected LocalDateTime visitEnd;

    @Column(name = "DESCRIPTION", length = 4000)
    protected String description;

    public User getAssignedNurse() {
        return assignedNurse;
    }

    public void setAssignedNurse(User assignedNurse) {
        this.assignedNurse = assignedNurse;
    }

    @Transient
    @MetaProperty(related = "pet")
    public String getPetName() {
        return Optional.ofNullable(getPet())
                .map(NamedEntity::getName)
                .orElse("");
    }

    @Transient
    @MetaProperty(related = "type")
    public String getTypeStyle() {
        return Optional.ofNullable(getType())
                .map(VisitType::getStyleName)
                .orElse("");
    }

    @Column(name = "TREATMENT_STATUS")
    private String treatmentStatus;

    public VisitTreatmentStatus getTreatmentStatus() {
        return treatmentStatus == null ? null : fromId(treatmentStatus);
    }

    public void setTreatmentStatus(VisitTreatmentStatus treatmentStatus) {
        this.treatmentStatus = treatmentStatus == null ? null : treatmentStatus.getId();
    }

    public VisitType getType() {
        return type == null ? null : VisitType.fromId(type);
    }

    public void setType(VisitType type) {
        this.type = type == null ? null : type.getId();
    }

    public LocalDateTime getVisitEnd() {
        return visitEnd;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }

    public LocalDateTime getVisitStart() {
        return visitStart;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    public boolean hasStarted() {
        return inTreatmentStatus(IN_PROGRESS) || inTreatmentStatus(DONE);
    }

    public boolean hasFinished() {
        return inTreatmentStatus(DONE);
    }

    private boolean inTreatmentStatus(VisitTreatmentStatus inProgress) {
        return getTreatmentStatus().equals(inProgress);
    }

}