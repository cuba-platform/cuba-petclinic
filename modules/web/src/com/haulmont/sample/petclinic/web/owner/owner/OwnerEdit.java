package com.haulmont.sample.petclinic.web.owner.owner;

import com.haulmont.sample.petclinic.entity.owner.Owner;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_Owner.edit")
@UiDescriptor("owner-edit.xml")
@EditedEntityContainer("ownerCt")
public class OwnerEdit extends StandardEditor<Owner> {
}