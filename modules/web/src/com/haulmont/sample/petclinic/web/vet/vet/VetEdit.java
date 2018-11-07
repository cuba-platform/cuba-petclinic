package com.haulmont.sample.petclinic.web.vet.vet;

import com.haulmont.sample.petclinic.entity.vet.Vet;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_Vet.edit")
@UiDescriptor("vet-edit.xml")
@EditedEntityContainer("vetCt")
public class VetEdit extends StandardEditor<Vet> {

}