package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.CheckBoxGroup;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.OptionsGroup;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import com.haulmont.sample.petclinic.entity.visit.VisitType;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;

@UiController("petclinic_Visit.browse")
@UiDescriptor("visit-browse.xml")
@LookupComponent("visitsTable")
@LoadDataBeforeShow
public class VisitBrowse extends StandardLookup<Visit> {


    @Inject
    protected Calendar<LocalDateTime> calendar;

    @Inject
    protected CollectionLoader<Visit> visitsCalendarDl;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected CollectionContainer<Visit> visitsCalendarDc;
    @Inject
    protected CollectionLoader<Visit> visitsDl;

    @Inject
    protected DataContext dataContext;
    @Inject
    protected CheckBoxGroup<VisitType> typeMultiFilter;

    @Subscribe
    protected void onInit(InitEvent event) {
        typeMultiFilter.setOptionsEnum(VisitType.class);
        typeMultiFilter.setOptionIconProvider(o -> VisitTypeIcon.valueOf(o.getIcon()).source());
        typeMultiFilter.setValue(Arrays.asList(VisitType.values()));
    }




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

    @Subscribe("calendar")
    protected void onCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {

        Screen visitEditor = screenBuilders.editor(Visit.class, this)
                .editEntity((Visit) event.getEntity())
                .withOpenMode(OpenMode.DIALOG)
                .build();

        visitEditor.addAfterCloseListener(afterCloseEvent -> {
            if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                getScreenData().loadAll();
            }
        });

        visitEditor.show();
    }

    @Subscribe("typeFilter")
    protected void onTypeFilterValueChange(HasValue.ValueChangeEvent<VisitType> event) {
        if (event.getValue() != null) {
            visitsCalendarDl.setParameter("type", event.getValue());
        } else {
            visitsCalendarDl.removeParameter("type");
        }
        visitsCalendarDl.load();
    }

    @Subscribe("typeMultiFilter")
    protected void onTypeMultiFilterValueChange(HasValue.ValueChangeEvent event) {
        /*if (event.getValue() != null) {
            visitsCalendarDl.setParameter("type", event.getValue());
        } else {
            visitsCalendarDl.removeParameter("type");
        }
        visitsCalendarDl.load();*/
    }


    @Subscribe("calendar")
    protected void onCalendarCalendarEventResize(Calendar.CalendarEventResizeEvent<LocalDateTime> event) {
        updateVisit(event.getEntity(), event.getNewStart(), event.getNewEnd());
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarEventMove(Calendar.CalendarEventMoveEvent<LocalDateTime> event) {
        updateVisit(event.getEntity(), event.getNewStart(), event.getNewEnd());
    }

    private void updateVisit(Entity entity, LocalDateTime newStart, LocalDateTime newEnd) {
        Visit visit = (Visit) entity;
        visit.setVisitStart(newStart);
        visit.setVisitEnd(newEnd);
        dataContext.commit();
    }


}