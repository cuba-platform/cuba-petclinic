package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.Button



class VeterinarianBrowse extends Composite<VeterinarianBrowse> {

    @Wire(path = 'createBtn')
    public Button create
}
