package com.haulmont.sample.petclinic.web.vet.vet;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.vet.Vet;

@UiController("petclinic_Vet.edit")
@UiDescriptor("vet-edit.xml")
@EditedEntityContainer("vetDc")
@LoadDataBeforeShow
public class VetEdit extends StandardEditor<Vet> {

}