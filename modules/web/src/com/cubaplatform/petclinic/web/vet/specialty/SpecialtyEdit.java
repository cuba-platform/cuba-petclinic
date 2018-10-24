package com.cubaplatform.petclinic.web.vet.specialty;

import com.cubaplatform.petclinic.entity.pet.PetType;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cubaplatform.petclinic.entity.vet.Specialty;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_Specialty.edit")
@UiDescriptor("specialty-edit.xml")
@EditedEntityContainer("specialtyCt")
public class SpecialtyEdit extends StandardEditor<Specialty> {

  @Subscribe
  protected void onBeforeShow(BeforeShowEvent beforeShowEvent) {
    getScreenData().loadAll();
  }
}