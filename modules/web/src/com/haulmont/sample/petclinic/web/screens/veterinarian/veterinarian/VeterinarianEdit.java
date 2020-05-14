package com.haulmont.sample.petclinic.web.screens.veterinarian.veterinarian;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.veterinarian.Veterinarian;

@UiController("petclinic_Veterinarian.edit")
@UiDescriptor("veterinarian-edit.xml")
@EditedEntityContainer("veterinarianDc")
@LoadDataBeforeShow
@Route(value = "veterinarians/edit", parentPrefix = "veterinarians")
public class VeterinarianEdit extends StandardEditor<Veterinarian> {

}