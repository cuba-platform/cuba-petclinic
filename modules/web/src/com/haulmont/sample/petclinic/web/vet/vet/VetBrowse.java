package com.haulmont.sample.petclinic.web.vet.vet;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.vet.Veterinarian;

@UiController("petclinic_Veterinarian.browse")
@UiDescriptor("vet-browse.xml")
@LookupComponent("vetsTable")
@LoadDataBeforeShow
@Route("vets")
public class VetBrowse extends StandardLookup<Veterinarian> {

}