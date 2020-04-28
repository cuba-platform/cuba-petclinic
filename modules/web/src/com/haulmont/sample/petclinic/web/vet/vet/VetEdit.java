package com.haulmont.sample.petclinic.web.vet.vet;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.vet.Vet;

@UiController("petclinic_Vet.edit")
@UiDescriptor("vet-edit.xml")
@EditedEntityContainer("vetDc")
@LoadDataBeforeShow
@Route(value = "vets/edit", parentPrefix = "vets")
public class VetEdit extends StandardEditor<Vet> {

}