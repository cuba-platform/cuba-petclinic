package com.haulmont.sample.petclinic.web.owner.owner;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.owner.Owner;

@UiController("petclinic_Owner.browse")
@UiDescriptor("owner-browse.xml")
@LookupComponent("ownersTable")
@LoadDataBeforeShow
@Route("owners")
public class OwnerBrowse extends StandardLookup<Owner> {
}