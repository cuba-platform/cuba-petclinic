package com.haulmont.sample.petclinic.core.role;

import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.app.role.annotation.SpecificAccess;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;
import com.haulmont.cuba.security.role.SpecificPermissionsContainer;

@Role(name = EmployeeRole.NAME)
public class EmployeeRole extends AnnotatedRoleDefinition {

    public final static String NAME = "Employee";

    @ScreenAccess(screenIds = {
        "application-petclinic",
        "application-masterdata",
        "help",
        "aboutWindow",
        "settings",
        "date-interval-editor",
        "bulkEditor",
    })
    @Override
    public ScreenPermissionsContainer screenPermissions() {
        return super.screenPermissions();
    }

    @SpecificAccess(permissions = {
        "cuba.gui.filter.edit",
        "cuba.gui.filter.customConditions",
        "cuba.gui.filter.global",
        "cuba.gui.filter.maxResults",
        "cuba.gui.bulkEdit"
    })
    @Override
    public SpecificPermissionsContainer specificPermissions() {
        return super.specificPermissions();
    }
}
