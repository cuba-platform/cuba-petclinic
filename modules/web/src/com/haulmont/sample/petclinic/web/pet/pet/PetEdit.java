package com.haulmont.sample.petclinic.web.pet.pet;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.pet.Pet;

@UiController("petclinic_Pet.edit")
@UiDescriptor("pet-edit.xml")
@EditedEntityContainer("petDc")
@LoadDataBeforeShow
@Route(value = "pets/edit", parentPrefix = "pets")
public class PetEdit extends StandardEditor<Pet> {
}