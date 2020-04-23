package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.visit.Visit;

import javax.inject.Inject;
import java.time.LocalDateTime;

@UiController("petclinic_Visit.browse")
@UiDescriptor("visit-browse.xml")
@LookupComponent("visitsTable")
@LoadDataBeforeShow
public class VisitBrowse extends StandardLookup<Visit> {


    @Inject
    protected Calendar<LocalDateTime> calendar;

    @Inject
    protected CollectionLoader<Visit> visitsCalendarDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        loadEvents();
    }

    @Subscribe("calendar")
    public void onCalendarBackwardClick(Calendar.CalendarBackwardClickEvent<LocalDateTime> event) {
        calendar.setStartDate(calendar.getStartDate().minusWeeks(1));
        calendar.setEndDate(calendar.getEndDate().minusWeeks(1));
        loadEvents();
    }

    @Subscribe("calendar")
    public void onCalendarForwardClick(Calendar.CalendarForwardClickEvent<LocalDateTime> event) {
        calendar.setStartDate(calendar.getStartDate().plusWeeks(1));
        calendar.setEndDate(calendar.getEndDate().plusWeeks(1));
        loadEvents();
    }

    private void loadEvents() {
        visitsCalendarDl.setParameter("visitStart", calendar.getStartDate());
        visitsCalendarDl.setParameter("visitEnd", calendar.getEndDate());
        visitsCalendarDl.load();
    }

}