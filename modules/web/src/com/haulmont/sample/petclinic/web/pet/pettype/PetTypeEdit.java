package com.haulmont.sample.petclinic.web.pet.pettype;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.pet.PetType;

@UiController("petclinic_PetType.edit")
@UiDescriptor("pet-type-edit.xml")
@EditedEntityContainer("petTypeCt")
@LoadDataBeforeShow
public class PetTypeEdit extends StandardEditor<PetType> {

}