package com.haulmont.sample.petclinic.entity.visit;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum VisitType implements EnumClass<String> {

    REGULAR_CHECKUP("REGULAR_CHECKUP", "event-blue"),
    RECHARGE("RECHARGE", "event-green"),
    STATUS_CONDITION_HEALING("STATUS_CONDITION_HEALING", "event-yellow"),
    DISEASE_TREATMENT("DISEASE_TREATMENT", "event-red"),
    OTHER("OTHER", "event-purple");


    private String id;
    private String styleName;

    VisitType(String value, String styleName) {
        this.id = value;
        this.styleName = styleName;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static VisitType fromId(String id) {
        for (VisitType at : VisitType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }

    public String getStyleName() {
        return styleName;
    }
}