package com.cubaplatform.petclinic.web.pet.pet;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;

public class PetBrowse extends AbstractLookup {

    public void createDiseaseWarningMailing() {
        openWindow("create-disease-warning-mailing", WindowManager.OpenType.DIALOG);
    }
}