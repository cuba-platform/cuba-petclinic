package com.haulmont.sample.petclinic.core.role;

import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;

@Role(name = EmployeeRole.NAME)
public class EmployeeRole extends AnnotatedRoleDefinition {

  public final static String NAME = "Employee";

  @ScreenAccess(screenIds = {
      "application-petclinic",
      "application-masterdata",
      "help",
      "aboutWindow",
      "settings",
  })
  @Override
  public ScreenPermissionsContainer screenPermissions() {
    return super.screenPermissions();
  }

}
