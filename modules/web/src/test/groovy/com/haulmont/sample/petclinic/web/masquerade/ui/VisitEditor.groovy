package com.haulmont.sample.petclinic.web.masquerade.ui


import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.*

@Wire(path = 'dialog_petclinic_Visit.edit')
class VisitEditor extends Composite<VisitEditor> {

    @Wire(path = ['visitStart', 'datepart'])
    public DateField datepartStart

/*    public SelenideElement timepartStart = $(byChain(byCubaId('visitStart'),
            byCubaId('timepart'),
            TagNames.DIV,
            TagNames.INPUT))*/

    @Wire(path = ['visitEnd', 'datepart'])
    public DateField datepartEnd

/*    public SelenideElement timepartEnd = $(byChain(byCubaId('visitEnd'),
            byCubaId('timepart'),
            TagNames.DIV,
            TagNames.INPUT))*/

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

    @Wire(path = 'windowClose')
    public Button cancel

    @Wire
    public LookupField treatmentStatusField
}
