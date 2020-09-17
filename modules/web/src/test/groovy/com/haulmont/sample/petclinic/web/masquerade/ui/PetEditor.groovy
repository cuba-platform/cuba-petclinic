package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.DateField
import com.haulmont.masquerade.components.LookupField
import com.haulmont.masquerade.components.LookupPickerField
import com.haulmont.masquerade.components.Table
import com.haulmont.masquerade.components.TextField

class PetEditor extends Composite<PetEditor> {
    @Wire
    public TextField name

    @Wire
    public TextField identificationNumber

    @Wire
    public TextField idFilterField

    @Wire
    public LookupField typeFilterField

    @Wire
    public DateField datepart

    @Wire
    public LookupPickerField type

    @Wire(path = "owner")
    public LookupPickerField ownerField

    @Wire(path = 'windowCommitAndClose')
    public Button ok

    @Wire
    public Table petsTable

    @Wire(path = 'lookupSelectAction')
    public Button select

}
