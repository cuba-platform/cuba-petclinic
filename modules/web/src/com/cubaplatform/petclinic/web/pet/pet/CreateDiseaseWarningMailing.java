package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.entity.pet.PetType;
import com.cubaplatform.petclinic.service.visit.DiseaseWarningMailingService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController
@UiDescriptor("create-disease-warning-mailing.xml")
public class CreateDiseaseWarningMailing extends Screen {

    @Inject
    protected DiseaseWarningMailingService diseaseWarningMailingService;

    @Inject
    protected TextField<String> city;

    @Inject
    protected TextField<String> disease;

    @Inject
    protected LookupField<PetType> petType;

    @Inject
    protected Notifications notifications;

    @Subscribe(id = "createDiseaseWarningMailing")
    protected void createDiseaseWarningMailing(Action.ActionPerformedEvent event) {

        int endangeredPets = diseaseWarningMailingService.warnAboutDisease(petType.getValue(),
                disease.getValue(), city.getValue());

        closeWithCommit().then(() ->
                notifications.create()
                        .setCaption(endangeredPets + " Owner(s) of endangered Pets have been notified")
                        .setType(Notifications.NotificationType.TRAY)
                        .show()
        );
    }
}