package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.TextField

class OwnerEditor extends Composite<OwnerEditor>{
    @Wire
    public TextField firstName

    @Wire
    public TextField address

    @Wire
    public TextField telephone

    @Wire
    public TextField lastName

    @Wire
    public TextField city

    @Wire
    public TextField email

    @Wire(path = 'windowCommitAndClose')
    public Button ok

}
