package com.haulmont.sample.petclinic.entity.visit;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum VisitType implements EnumClass<String> {

    REGULAR_CHECKUP("REGULAR_CHECKUP", "event-blue", "BLUE"),
    RECHARGE("RECHARGE", "event-green", "GREEN"),
    STATUS_CONDITION_HEALING("STATUS_CONDITION_HEALING", "event-yellow", "YELLOW"),
    DISEASE_TREATMENT("DISEASE_TREATMENT", "event-red", "RED"),
    OTHER("OTHER", "event-purple", "PURPLE");


    private String id;
    private String styleName;
    private final String icon;

    VisitType(String value, String styleName, String icon) {
        this.id = value;
        this.styleName = styleName;
        this.icon = icon;
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

    public String getIcon() {
        return icon;
    }
}