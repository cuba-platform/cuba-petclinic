package com.haulmont.sample.petclinic.web.pet.pet;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Slider;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.owner.Owner;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.pet.PetType;

import javax.inject.Inject;

@UiController("petclinic_Pet.browse")
@UiDescriptor("pet-browse.xml")
@LookupComponent("petsTable")
@LoadDataBeforeShow
@Route("pets")
public class PetBrowse extends StandardLookup<Pet> {

    @Inject
    protected Slider birthDateFilterField;

    @Inject
    protected TextField<String> idFilterField;

    @Inject
    protected LookupField<Owner> ownerFilterField;

    @Inject
    protected LookupField<PetType> typeFilterField;


    @Subscribe("petsTable.clearFilter")
    protected void onPetsTableClearFilter(Action.ActionPerformedEvent event) {
        typeFilterField.setValue(null);
        ownerFilterField.setValue(null);
        idFilterField.setValue(null);
        birthDateFilterField.setValue(null);
    }

}