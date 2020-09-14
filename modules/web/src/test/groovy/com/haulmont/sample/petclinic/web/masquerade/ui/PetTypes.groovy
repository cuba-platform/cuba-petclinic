package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.Table
import com.haulmont.masquerade.components.TextField

class PetTypes extends Composite<PetTypes> {
    @Wire
    public Button createBtn

    @Wire
    public TextField nameField

    @Wire
    public Button saveBtn

    @Wire
    public Table table

    @Wire(path = 'lookupSelectAction')
    public Button select

}
