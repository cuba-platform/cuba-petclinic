package com.haulmont.sample.petclinic.web.pet.pettype;

import com.haulmont.sample.petclinic.entity.pet.PetType;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_PetType.browse")
@UiDescriptor("pet-type-browse.xml")
@LookupComponent("petTypesTable")
public class PetTypeBrowse extends StandardLookup<PetType> {
}