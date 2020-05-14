package com.haulmont.sample.petclinic.web.screens.veterinarian.specialty;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.veterinarian.Specialty;

@UiController("petclinic_Specialty.browse")
@UiDescriptor("specialty-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
@Route("specialties")
public class SpecialtyBrowse extends MasterDetailScreen<Specialty> {
}