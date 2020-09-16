package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.*
import com.haulmont.masquerade.sys.TagNames

import static com.codeborne.selenide.Selenide.$
import static com.haulmont.masquerade.Selectors.byChain
import static com.haulmont.masquerade.Selectors.byCubaId

@Wire(path = 'dialog_petclinic_Visit.edit')
class VisitEditor extends Composite<VisitEditor> {

    @Wire(path = ['visitStart', 'datepart'])
    public DateField datepartStart

    public TimeField timepartStart = $(byChain(byCubaId('visitStart'),
            byCubaId('timepart'),
            TagNames.DIV,
            TagNames.INPUT)) as TimeField

    @Wire(path = ['visitEnd', 'datepart'])
    public DateField datepartEnd

    public TimeField timepartEnd = $(byChain(byCubaId('visitEnd'),
            byCubaId('timepart'),
            TagNames.DIV,
            TagNames.INPUT)) as TimeField

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
