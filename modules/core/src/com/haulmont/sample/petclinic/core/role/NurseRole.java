package com.haulmont.sample.petclinic.core.role;

import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.Role;

@Role(name = NurseRole.NAME)
public class NurseRole extends AnnotatedRoleDefinition {

  public final static String NAME = "Nurse";
}
