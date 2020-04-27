package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.cuba.core.entity.Entity;
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
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigation;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigationMode;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigators;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarMode;
import com.vaadin.v7.ui.components.calendar.CalendarComponentEvents;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

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
    protected RadioButtonGroup<CalendarMode> calendarRange;
    @Inject
    protected TimeSource timeSource;

    @Inject
    protected Label<String> calendarRangeDescription;

    @Inject
    protected CalendarNavigators calendarNavigators;

    @Inject
    protected DatePicker<LocalDate> calendarRangePicker;
    @Inject
    protected UserSessionSource userSessionSource;

    @Subscribe
    protected void onInit(InitEvent event) {
        typeMultiFilter.setOptionsEnum(VisitType.class);
        typeMultiFilter.setValue(EnumSet.allOf(VisitType.class));
        typeMultiFilter.setOptionIconProvider(o -> VisitTypeIcon.valueOf(o.getIcon()).source());

        calendarRange.setOptionsEnum(CalendarMode.class);
        calendarRangePicker.setValue(timeSource.now().toLocalDate());

        registerDateClickEventHandler();
    }

    @SuppressWarnings("deprecation")
    private void registerDateClickEventHandler() {
        calendar.unwrap(com.vaadin.v7.ui.Calendar.class)
                .setHandler((CalendarComponentEvents.DateClickHandler) event -> atDate(CalendarMode.DAY, toLocalDate(event.getDate())));
    }


    private LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarDayClick(Calendar.CalendarDayClickEvent<LocalDateTime> event) {
        // TODO: switch from direct Vaadin handler to this method
//        atDate(CalendarMode.DAY, event.getDate().toLocalDate());
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarWeekClick(Calendar.CalendarWeekClickEvent<LocalDateTime> event) {
        atDate(CalendarMode.WEEK, firstDayOfWeek(event));
    }

    private LocalDate firstDayOfWeek(Calendar.CalendarWeekClickEvent<LocalDateTime> event) {
        WeekFields weekFields = WeekFields.of(userSessionSource.getLocale());
        return timeSource.now().toLocalDateTime()
                .withYear(event.getYear())
                .with(weekFields.weekOfYear(), event.getWeek())
                .with(weekFields.dayOfWeek(), 1)
                .toLocalDate();
    }


    @Subscribe("navigatorPrevious")
    protected void onNavigatorPreviousClick(Button.ClickEvent event) {
        previous(calendarRange.getValue());
    }

    @Subscribe("navigatorNext")
    protected void onNavigatorNextClick(Button.ClickEvent event) {
        next(calendarRange.getValue());
    }

    @Subscribe("navigatorCurrent")
    protected void onNavigatorCurrentClick(Button.ClickEvent event) {
        current(calendarRange.getValue());
    }

    @Subscribe("calendarRangePicker")
    protected void onCalendarRangePickerValueChange(HasValue.ValueChangeEvent<LocalDate> event) {
        if (event.isUserOriginated()) {
            atDate(calendarRange.getValue(), event.getValue());
        }
    }

    private void current(CalendarMode calendarMode) {
        change(calendarMode, CalendarNavigationMode.PREVIOUS, timeSource.now().toLocalDate());
    }

    private void atDate(CalendarMode calendarMode, LocalDate date) {
        change(calendarMode, CalendarNavigationMode.AT_DATE, date);
    }

    private void next(CalendarMode calendarMode) {
        change(calendarMode, CalendarNavigationMode.NEXT, calendarRangePicker.getValue());
    }

    private void previous(CalendarMode calendarMode) {
        change(calendarMode, CalendarNavigationMode.PREVIOUS, calendarRangePicker.getValue());
    }

    private void change(CalendarMode calendarMode, CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        calendarRange.setValue(calendarMode);
        String description = performChange(calendarMode, navigationMode, referenceDate);
        calendarRangeDescription.setValue(description);
        loadEvents();
    }

    private String performChange(CalendarMode calendarMode, CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        CalendarNavigation navigator = calendarNavigators.forMode(calendar, calendarRangePicker, calendarMode);

        switch (navigationMode) {
            case NEXT: return navigator.next(referenceDate);
            case PREVIOUS: return navigator.previous(referenceDate);
            case CURRENT:
            case AT_DATE: return navigator.atDate(referenceDate);
        }
        return null;
    }


    @Subscribe("calendarRange")
    protected void onCalendarRangeValueChange(HasValue.ValueChangeEvent event) {
        if (event.isUserOriginated()) {
            atDate((CalendarMode) event.getValue(), calendarRangePicker.getValue());
        }
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        current(CalendarMode.WEEK);
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