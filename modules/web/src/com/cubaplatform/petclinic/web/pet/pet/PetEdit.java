package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.entity.pet.Pet;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_Pet.edit")
@UiDescriptor("pet-edit.xml")
@EditedEntityContainer("petCt")
public class PetEdit extends StandardEditor<Pet> {
}