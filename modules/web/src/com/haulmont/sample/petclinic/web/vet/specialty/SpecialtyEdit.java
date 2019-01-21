package com.haulmont.sample.petclinic.web.vet.specialty;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.vet.Specialty;

@UiController("petclinic_Specialty.edit")
@UiDescriptor("specialty-edit.xml")
@EditedEntityContainer("specialtyDc")
@LoadDataBeforeShow
public class SpecialtyEdit extends StandardEditor<Specialty> {
}