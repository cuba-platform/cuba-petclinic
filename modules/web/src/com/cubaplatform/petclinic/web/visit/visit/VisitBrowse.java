package com.cubaplatform.petclinic.web.visit.visit;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;

public class VisitBrowse extends AbstractLookup {

    public void createForPet() {
        openWindow("create-visit-for-pet", WindowManager.OpenType.DIALOG);
    }
}