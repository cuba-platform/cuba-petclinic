package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import com.haulmont.sample.petclinic.web.visit.visit.CreateVisitForPet;
import javax.inject.Inject;

@UiController("petclinic_Visit.browse")
@UiDescriptor("visit-browse.xml")
@LookupComponent("visitsTable")
public class VisitBrowse extends StandardLookup<Visit> {

  @Inject
  private Screens screens;

  @Subscribe("visitsTable.createForPet")
  public void createForPet(Action.ActionPerformedEvent event) {
    screens.create(CreateVisitForPet.class, OpenMode.DIALOG).show();
  }
}