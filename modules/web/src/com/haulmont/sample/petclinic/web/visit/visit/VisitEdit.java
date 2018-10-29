package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.sample.petclinic.entity.visit.Visit;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;


@UiController("petclinic_Visit.edit")
@UiDescriptor("visit-edit.xml")
@EditedEntityContainer("visitCt")
public class VisitEdit extends StandardEditor<Visit> {

}