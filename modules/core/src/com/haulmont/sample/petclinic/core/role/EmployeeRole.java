package com.haulmont.sample.petclinic.core.role;

import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.EntityAccess;
import com.haulmont.cuba.security.app.role.annotation.EntityAttributeAccess;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;
import com.haulmont.sample.petclinic.entity.owner.Owner;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.pet.PetType;
import com.haulmont.sample.petclinic.entity.vet.Specialty;
import com.haulmont.sample.petclinic.entity.vet.Veterinarian;
import com.haulmont.sample.petclinic.entity.visit.Visit;

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
