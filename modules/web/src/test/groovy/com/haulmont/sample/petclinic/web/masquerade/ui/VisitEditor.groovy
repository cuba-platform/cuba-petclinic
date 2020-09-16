package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.DateField
import com.haulmont.masquerade.components.LookupField
import com.haulmont.masquerade.components.LookupPickerField
import com.haulmont.masquerade.components.TextArea
import com.haulmont.masquerade.components.TimeField

@Wire(path = 'dialog_petclinic_Visit.edit')
class VisitEditor extends Composite<VisitEditor>{

    @Wire(path = ['visitStart', 'datepart'])
    public DateField datepartStart

    @Wire(path = ['visitStart', 'timepart'])
    public TimeField timepartStart

    @Wire(path =['visitEnd', 'datepart'])
    public  DateField datepartEnd

    @Wire(path = ["visitEnd", "timepart"])
    public TimeField timepartEnd

    @Wire
    public LookupPickerField pet

    @Wire
    public LookupField typeField

    @Wire
    public LookupField assignedNurseField

    @Wire
    public TextArea description

    @Wire(path = 'windowCommitAndClose')
    public Button ok


}
