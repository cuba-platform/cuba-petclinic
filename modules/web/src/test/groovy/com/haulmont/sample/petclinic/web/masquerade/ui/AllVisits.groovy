package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire;
import com.haulmont.masquerade.base.Composite;
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.Table;

@Wire(path = "tableTab")
 class AllVisits extends Composite<AllVisits> {

    @Wire
    public Button createBtn

    @Wire
    public Table visitsTable
}
