package com.haulmont.sample.petclinic.web.screens.main;

import com.haulmont.cuba.gui.icons.Icons;

public enum SideMenuIcon implements Icons.Icon {

    ADMINISTRATION("theme:icons/sidemenu/icon_administration.svg", "administration"),
    HELP("theme:icons/sidemenu/icon_help.svg", "help"),
    MASTERDATA("theme:icons/sidemenu/icon_master_data.svg", "application-masterdata"),
    PETCLINIC("theme:icons/sidemenu/icon_petclinic.svg", "application-petclinic");

    protected String source;
    private final String menuId;

    SideMenuIcon(String source, String menuId) {
        this.source = source;
        this.menuId = menuId;
    }

    @Override
    public String source() {
        return source;
    }

    @Override
    public String iconName() {
        return name();
    }

    public String getMenuId() {
        return menuId;
    }
}