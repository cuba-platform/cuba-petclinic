package com.haulmont.sample.petclinic.web.vet.specialty;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.vet.Specialty;

@UiController("petclinic_Specialty.browse")
@UiDescriptor("specialty-browse.xml")
@LookupComponent("specialtiesTable")
@LoadDataBeforeShow
public class SpecialtyBrowse extends StandardLookup<Specialty> {
}