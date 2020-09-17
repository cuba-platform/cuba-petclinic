package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.Table

@Wire(path = 'dialog_petclinic_myVisits')
class CurrentVisits extends Composite<CurrentVisits>{
    @Wire
    public Table table

    @Wire
    public Button startBtn

    @Wire
    public Button finishBtn


}
