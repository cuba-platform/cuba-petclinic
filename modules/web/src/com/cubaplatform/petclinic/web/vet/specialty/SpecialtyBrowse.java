package com.cubaplatform.petclinic.web.vet.specialty;

import com.cubaplatform.petclinic.entity.vet.Specialty;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;


@UiController("petclinic_Specialty.browse")
@UiDescriptor("specialty-browse.xml")
@LookupComponent("specialtiesTable")
public class SpecialtyBrowse extends StandardLookup<Specialty> {

  @Subscribe
  protected void onBeforeShow(BeforeShowEvent beforeShowEvent) {
    getScreenData().loadAll();
  }

}