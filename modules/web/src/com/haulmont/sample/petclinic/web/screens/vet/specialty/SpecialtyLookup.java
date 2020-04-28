package com.haulmont.sample.petclinic.web.screens.vet.specialty;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.vet.Specialty;

@UiController("petclinic_Specialty.lookup")
@UiDescriptor("specialty-lookup.xml")
@LookupComponent("specialtiesTable")
@LoadDataBeforeShow
public class SpecialtyLookup extends StandardLookup<Specialty> {
}