package com.haulmont.sample.petclinic.web.screens.main;

import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.sample.petclinic.web.screens.visit.CalendarMode;
import javax.annotation.Nullable;

public enum HeliumThemeSwitchBtnMode {

    LIGHT("light", CubaIcon.MOON_O, "icon-only secondary dark-switch"),
    DARK("dark", CubaIcon.SUN_O, "icon-only secondary light-switch");

    private final String name;
    private final CubaIcon icon;
    private final String styleName;

    HeliumThemeSwitchBtnMode(String name, CubaIcon icon, String styleName) {
        this.name = name;
        this.icon = icon;
        this.styleName = styleName;
    }

    @Nullable
    public static HeliumThemeSwitchBtnMode fromId(String id) {
        for (HeliumThemeSwitchBtnMode at : HeliumThemeSwitchBtnMode.values()) {
            if (at.getName().equals(id)) {
                return at;
            }
        }
        return null;
    }

    public CubaIcon getIcon() {
        return icon;
    }

    public String getStyleName() {
        return styleName;
    }

    public String getName() {
        return name;
    }
}
