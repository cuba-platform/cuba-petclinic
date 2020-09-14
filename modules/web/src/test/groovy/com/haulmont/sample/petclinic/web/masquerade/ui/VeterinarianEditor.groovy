package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button
import com.haulmont.masquerade.components.TextField





class VeterinarianEditor extends Composite<VeterinarianEditor> {
    @Wire
    public TextField firstName

    @Wire
    public TextField lastName

    @Wire(path = 'windowCommitAndClose')
    public Button ok

    @Wire
    public Button add



}
