package com.cubaplatform.petclinic.web.vet.vet;

import com.cubaplatform.petclinic.entity.vet.Vet;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_Vet.browse")
@UiDescriptor("vet-browse.xml")
@LookupComponent("vetsTable")
public class VetBrowse extends StandardLookup<Vet> {

  @Subscribe
  protected void onBeforeShow(BeforeShowEvent beforeShowEvent) {
    getScreenData().loadAll();
  }

}