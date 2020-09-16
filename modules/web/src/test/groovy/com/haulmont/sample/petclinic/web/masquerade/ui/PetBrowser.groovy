package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.LookupField
import com.haulmont.masquerade.components.Table
import com.haulmont.masquerade.components.TextField

class PetBrowser extends Composite<PetBrowser> {

    @Wire
    public Button createBtn

    @Wire
    public Table petsTable

    @Wire
    public LookupField ownerFilterField

    @Wire
    public TextField idFilterField
}
