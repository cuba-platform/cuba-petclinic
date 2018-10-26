package com.cubaplatform.petclinic.web.vet.vet;

import com.cubaplatform.petclinic.entity.vet.Vet;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_Vet.edit")
@UiDescriptor("vet-edit.xml")
@EditedEntityContainer("vetCt")
public class VetEdit extends StandardEditor<Vet> {

}