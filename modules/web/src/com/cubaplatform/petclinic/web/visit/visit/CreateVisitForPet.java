package com.cubaplatform.petclinic.web.visit.visit;

import com.cubaplatform.petclinic.entity.visit.Visit;
import com.cubaplatform.petclinic.service.visit.VisitService;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;

public class CreateVisitForPet extends AbstractWindow {

    @Inject
    protected VisitService visitService;

    @Inject
    protected TextField identificationNumber;

    public void createVisit() {
        Visit visit = visitService.createVisitForToday(identificationNumber.getValue());

        showNotification("Visit created for " + visit.getPet().getInstanceName(), NotificationType.TRAY);

        closeAndRun(COMMIT_ACTION_ID, () -> openEditor(visit, WindowManager.OpenType.NEW_TAB));
    }
}