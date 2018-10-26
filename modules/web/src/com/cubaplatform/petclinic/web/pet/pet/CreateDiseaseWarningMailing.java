package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.entity.pet.PetType;
import com.cubaplatform.petclinic.service.visit.DiseaseWarningMailingService;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;

public class CreateDiseaseWarningMailing extends AbstractWindow {

    @Inject
    protected DiseaseWarningMailingService diseaseWarningMailingService;

    @Inject
    protected TextField city;

    @Inject
    protected TextField disease;

    @Inject
    protected LookupField petType;

    public void createDiseaseWarningMailing() {

        PetType petTypeValue = (PetType) petType.getValue();
        String diseaseValue = (String) disease.getValue();
        String cityValue = (String) city.getValue();

        int endangeredPets = diseaseWarningMailingService.warnAboutDisease(petTypeValue,
            diseaseValue, cityValue);

        closeAndRun(COMMIT_ACTION_ID, () -> showNotification(endangeredPets + " Owner(s) of endangered Pets have been notified", NotificationType.TRAY));

    }
}