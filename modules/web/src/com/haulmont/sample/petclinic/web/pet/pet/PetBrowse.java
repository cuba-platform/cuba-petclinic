package com.haulmont.sample.petclinic.web.pet.pet;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.pet.Pet;

@UiController("petclinic_Pet.browse")
@UiDescriptor("pet-browse.xml")
@LookupComponent("petsTable")
@LoadDataBeforeShow
public class PetBrowse extends StandardLookup<Pet> {
}