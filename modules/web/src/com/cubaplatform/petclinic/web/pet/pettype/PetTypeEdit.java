package com.cubaplatform.petclinic.web.pet.pettype;

import com.cubaplatform.petclinic.entity.pet.PetType;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_PetType.edit")
@UiDescriptor("pet-type-edit.xml")
@EditedEntityContainer("petTypeCt")
public class PetTypeEdit extends StandardEditor<PetType> {

}