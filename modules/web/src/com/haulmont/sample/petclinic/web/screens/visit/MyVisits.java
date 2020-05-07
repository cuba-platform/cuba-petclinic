package com.haulmont.sample.petclinic.web.screens.visit;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.sample.petclinic.entity.visit.Visit;

import javax.inject.Inject;

@UiController("petclinic_myVisits")
@UiDescriptor("my-visits.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class MyVisits extends MasterDetailScreen<Visit> {
    @Inject
    protected CollectionLoader<Visit> visitsDl;

    @Inject
    protected UserSession userSession;

    @Subscribe
    protected void onInit(InitEvent event) {
        visitsDl.setParameter("currentUser", userSession.getCurrentOrSubstitutedUser());
    }
}