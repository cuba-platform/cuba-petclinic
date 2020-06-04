package com.haulmont.sample.petclinic.web.screens.visit;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Notifications.NotificationType;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.Action.ActionPerformedEvent;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.sample.petclinic.entity.visit.Visit;

import com.haulmont.sample.petclinic.entity.visit.VisitTreatmentStatus;
import javax.inject.Inject;
import javax.inject.Named;

@UiController("petclinic_myVisits")
@UiDescriptor("my-visits.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class MyVisits extends MasterDetailScreen<Visit> {

  @Inject
  protected CollectionLoader<Visit> visitsDl;

  @Inject
  protected UserSession userSession;
  @Inject
  protected Table<Visit> table;
  @Inject
  protected Notifications notifications;

  @Named("table.edit")
  protected EditAction<Visit> tableEdit;
  @Inject
  protected UserSessionSource userSessionSource;
  @Inject
  protected DataContext dataContext;
  @Inject
  protected InstanceContainer<Visit> visitDc;
  @Inject
  protected DataManager dataManager;
  @Inject
  protected MessageBundle messageBundle;

  @Subscribe
  protected void onInit(InitEvent event) {
    visitsDl.setParameter("currentUser", userSession.getCurrentOrSubstitutedUser());

    tableEdit.withHandler(actionPerformedEvent -> {
      Visit item = table.getSingleSelected();
      if (item != null) {
        refreshOptionsForLookupFields();
        disableEditControls();
        getActionsPane().setVisible(true);
      }
    });
  }

  @Subscribe("table.start")
  protected void onStart(ActionPerformedEvent event) {
    final Visit visit = table.getSingleSelected();

    if (visit.hasStarted()) {
      petTreatmentWarningMessage("treatmentAlreadyStarted", visit.getPetName());
    }
    else {
      updateTreatmentTo(visit, VisitTreatmentStatus.IN_PROGRESS);
      petTreatmentSuccessMessage("treatmentStarted", visit.getPetName());
    }
  }

  @Subscribe("table.finish")
  protected void onTableFinish(ActionPerformedEvent event) {
    final Visit visit = table.getSingleSelected();

    if (visit.hasFinished()) {
      petTreatmentWarningMessage("treatmentAlreadyFinished", visit.getPetName());
    }
    else {
      updateTreatmentTo(visit, VisitTreatmentStatus.DONE);
      petTreatmentSuccessMessage("treatmentFinished", visit.getPetName());
    }
  }

  private void petTreatmentWarningMessage(String messageKey, String petName) {
    message(messageKey, petName, NotificationType.WARNING);
  }

  private void petTreatmentSuccessMessage(String messageKey, String petName) {
    message(messageKey, petName, NotificationType.TRAY);
  }

  private void message(String messageKey, String petName, NotificationType warning) {
    notifications.create(warning)
        .withCaption(messageBundle.formatMessage(messageKey, petName))
        .show();
  }
  private void updateTreatmentTo(Visit visitToStart, VisitTreatmentStatus targetStatus) {
    visitToStart.setTreatmentStatus(targetStatus);
    dataManager.commit(visitToStart);
    visitsDl.load();
  }

}