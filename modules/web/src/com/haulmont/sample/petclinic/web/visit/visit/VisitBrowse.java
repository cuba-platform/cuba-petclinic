package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import com.haulmont.sample.petclinic.entity.visit.VisitType;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigationMode;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigators;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarMode;
import com.vaadin.ui.InlineDateField;
import com.vaadin.v7.shared.ui.calendar.CalendarState;
import com.vaadin.v7.ui.components.calendar.CalendarComponentEvents;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.time.*;
import java.util.*;

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigationMode.*;
import static com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigationMode.PREVIOUS;
import static com.haulmont.sample.petclinic.web.visit.visit.calendar.RelativeDates.*;

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

    @Inject
    protected RadioButtonGroup<CalendarMode> calendarMode;
    @Inject
    protected TimeSource timeSource;

    @Inject
    protected Label<String> calendarTitle;

    @Inject
    protected CalendarNavigators calendarNavigators;

    @Inject
    protected DatePicker<LocalDate> calendarNavigator;
    @Inject
    protected UserSessionSource userSessionSource;
    @Inject
    protected DatatypeFormatter datatypeFormatter;

    @Subscribe
    protected void onInit(InitEvent event) {
        typeMultiFilter.setOptionsEnum(VisitType.class);
        typeMultiFilter.setValue(EnumSet.allOf(VisitType.class));
        typeMultiFilter.setOptionIconProvider(o -> VisitTypeIcon.valueOf(o.getIcon()).source());

        calendarMode.setOptionsEnum(CalendarMode.class);
        calendarNavigator.setValue(timeSource.now().toLocalDate());

        registerDateClickEventHandler();
        initSortForMonthlyView();
    }


    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        current(CalendarMode.WEEK);
    }

    @SuppressWarnings("deprecation")
    private void registerDateClickEventHandler() {
        calendar.unwrap(com.vaadin.v7.ui.Calendar.class)
                .setHandler((CalendarComponentEvents.DateClickHandler) event -> atDate(CalendarMode.DAY, event.getDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()));

        calendarNavigator.unwrap(InlineDateField.class)
                .set
    }

    @SuppressWarnings("deprecation")
    private void initSortForMonthlyView() {
        calendar.unwrap(com.vaadin.v7.ui.Calendar.class)
                .setEventSortOrder(CalendarState.EventSortOrder.START_DATE_DESC);
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarDayClick(Calendar.CalendarDayClickEvent<LocalDateTime> event) {
        // TODO: switch from direct Vaadin handler to this method
//        atDate(CalendarMode.DAY, event.getDate().toLocalDate());
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarWeekClick(Calendar.CalendarWeekClickEvent<LocalDateTime> event) {
        atDate(CalendarMode.WEEK, startOfWeek(event.getYear(), event.getWeek(), userSessionSource.getLocale()));
    }

    @Subscribe("navigatorPrevious")
    protected void onNavigatorPreviousClick(Button.ClickEvent event) {
        previous(calendarMode.getValue());
    }

    @Subscribe("navigatorNext")
    protected void onNavigatorNextClick(Button.ClickEvent event) {
        next(calendarMode.getValue());
    }

    @Subscribe("navigatorCurrent")
    protected void onNavigatorCurrentClick(Button.ClickEvent event) {
        current(calendarMode.getValue());
    }

    @Subscribe("calendarNavigator")
    protected void onCalendarRangePickerValueChange(HasValue.ValueChangeEvent<LocalDate> event) {
        if (event.isUserOriginated()) {
            atDate(calendarMode.getValue(), event.getValue());
        }
    }

    private void current(CalendarMode calendarMode) {
        change(calendarMode, AT_DATE, timeSource.now().toLocalDate());
    }

    private void atDate(CalendarMode calendarMode, LocalDate date) {
        change(calendarMode, AT_DATE, date);
    }

    private void next(CalendarMode calendarMode) {
        change(calendarMode, NEXT, calendarNavigator.getValue());
    }

    private void previous(CalendarMode calendarMode) {
        change(calendarMode, PREVIOUS, calendarNavigator.getValue());
    }

    private void change(CalendarMode calendarMode, CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        this.calendarMode.setValue(calendarMode);

        String description = calendarNavigators
                .forMode(calendar, calendarNavigator, datatypeFormatter, calendarMode)
                .navigate(navigationMode, referenceDate);

        calendarTitle.setValue(description);

        loadEvents();
    }


    @Subscribe("calendarMode")
    protected void onCalendarRangeValueChange(HasValue.ValueChangeEvent event) {
        if (event.isUserOriginated()) {
            atDate((CalendarMode) event.getValue(), calendarNavigator.getValue());
        }
    }

    private void loadEvents() {
        visitsCalendarDl.setParameter("visitStart", calendar.getStartDate());
        visitsCalendarDl.setParameter("visitEnd", calendar.getEndDate());
        visitsCalendarDl.load();
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Calendar Visit Event Click
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
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


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Filter for Visit Types
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Subscribe("typeMultiFilter")
    protected void onTypeMultiFilterValueChange(HasValue.ValueChangeEvent event) {

        if (event.getValue() == null) {
            visitsCalendarDl.removeParameter("type");
        } else if (CollectionUtils.isEmpty((Set<VisitType>) event.getValue())) {
            visitsCalendarDl.setParameter("type", Collections.singleton(""));
        } else {
            visitsCalendarDl.setParameter("type", event.getValue());
        }
        loadEvents();
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Visit Changes through Calendar Event Adjustments
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

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