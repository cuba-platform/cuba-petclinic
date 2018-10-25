package com.cubaplatform.petclinic.web.owner.owner;

import com.cubaplatform.petclinic.entity.owner.Owner;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_Owner.edit")
@UiDescriptor("owner-edit.xml")
@EditedEntityContainer("ownerCt")
public class OwnerEdit extends StandardEditor<Owner> {

  @Subscribe
  protected void onBeforeShow(BeforeShowEvent beforeShowEvent) {
    getScreenData().loadAll();
  }
}