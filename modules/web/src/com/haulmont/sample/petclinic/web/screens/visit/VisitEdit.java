package com.haulmont.sample.petclinic.web.screens.visit;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.visit.Visit;

@UiController("petclinic_Visit.edit")
@UiDescriptor("visit-edit.xml")
@EditedEntityContainer("visitDc")
@LoadDataBeforeShow
@Route(value = "visits/edit", parentPrefix = "visits")
public class VisitEdit extends StandardEditor<Visit> {

}