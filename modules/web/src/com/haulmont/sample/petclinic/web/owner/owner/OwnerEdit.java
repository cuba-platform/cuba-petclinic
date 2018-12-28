package com.haulmont.sample.petclinic.web.owner.owner;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.owner.Owner;

@UiController("petclinic_Owner.edit")
@UiDescriptor("owner-edit.xml")
@EditedEntityContainer("ownerCt")
@LoadDataBeforeShow
public class OwnerEdit extends StandardEditor<Owner> {
}