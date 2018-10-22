package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.entity.pet.Pet;
import com.haulmont.cuba.gui.screen.*;

@UiController("sad_Pet.edit")
@UiDescriptor("pet-edit.xml")
@EditedEntityContainer("petCt")
public class PetEdit extends StandardEditor<Pet> {

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent beforeShowEvent) {
        getScreenData().loadAll();
    }
}