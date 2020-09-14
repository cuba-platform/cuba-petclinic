package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.Table
import com.haulmont.masquerade.components.TextField

class Specialties extends Composite<Specialties> {

    @Wire(path = 'specialtiesTable_composition')
    public Table table

    @Wire (path = 'lookupSelectAction')
    public Button selectButton

    @Wire (path = 'table_composition')
    public Table tableSpecialists

    @Wire
    public Button createBtn

    @Wire
    public TextField nameField

    @Wire
    public Button saveBtn
}
