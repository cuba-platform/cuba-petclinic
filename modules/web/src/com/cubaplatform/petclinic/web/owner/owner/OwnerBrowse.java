package com.cubaplatform.petclinic.web.owner.owner;

import com.cubaplatform.petclinic.entity.owner.Owner;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("petclinic_Owner.browse")
@UiDescriptor("owner-browse.xml")
@LookupComponent("ownersTable")
public class OwnerBrowse extends StandardLookup<Owner> {
}