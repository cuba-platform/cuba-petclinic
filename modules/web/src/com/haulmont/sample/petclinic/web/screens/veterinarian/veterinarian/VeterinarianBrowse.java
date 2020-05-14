package com.haulmont.sample.petclinic.web.screens.veterinarian.veterinarian;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.veterinarian.Veterinarian;

@UiController("petclinic_Veterinarian.browse")
@UiDescriptor("veterinarian-browse.xml")
@LookupComponent("veterinariansTable")
@LoadDataBeforeShow
@Route("veterinarians")
public class VeterinarianBrowse extends StandardLookup<Veterinarian> {

}