package com.haulmont.sample.petclinic.web.screens.visit;

import com.haulmont.cuba.gui.icons.Icons;

public enum VisitTypeIcon implements Icons.Icon {

    PURPLE("theme:icons/purple.png"),
    YELLOW("theme:icons/yellow.png"),
    RED("theme:icons/red.png"),
    GREEN("theme:icons/green.png"),
    BLUE("theme:icons/blue.png");

    protected String source;

    VisitTypeIcon(String source) {
        this.source = source;
    }

    @Override
    public String source() {
        return source;
    }

    @Override
    public String iconName() {
        return name();
    }

}