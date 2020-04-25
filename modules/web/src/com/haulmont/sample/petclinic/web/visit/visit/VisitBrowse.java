package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.Notifications;
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
    protected Notifications notifications;
    @Inject
    protected UserSessionSource userSessionSource;

    @Subscribe
    protected void onInit(InitEvent event) {
        typeMultiFilter.setOptionsEnum(VisitType.class);
        typeMultiFilter.setOptionIconProvider(o -> VisitTypeIcon.valueOf(o.getIcon()).source());

        calendarRange.setOptionsEnum(CalendarMode.class);

        calendarRangePicker.setValue(timeSource.now().toLocalDate());

        com.vaadin.v7.ui.Calendar vaadinCalendar = calendar.unwrap(com.vaadin.v7.ui.Calendar.class);

        vaadinCalendar.setHandler(new CalendarComponentEvents.DateClickHandler() {
            @Override
            public void dateClick(CalendarComponentEvents.DateClickEvent event) {
//                calendarRange.setValue(CalendarMode.DAY);
//                atDate(CalendarMode.DAY, toLocalDate(event.getDate()));
//                loadEvents();

                notifications.create(Notifications.NotificationType.TRAY)
                        .withCaption("Vaadins CalendarComponentEvents.DateClickEvent fired")
                        .show();
            }
        });
    }


    private LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarDayClick(Calendar.CalendarDayClickEvent<LocalDateTime> event) {
//        calendarRange.setValue(CalendarMode.DAY);
//        atDate(CalendarMode.DAY, event.getDate().toLocalDate());
//        loadEvents();
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("CUBAs Calendar.CalendarDayClickEvent fired")
                .show();
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarWeekClick(Calendar.CalendarWeekClickEvent<LocalDateTime> event) {
//        WeekFields weekFields = WeekFields.of(userSessionSource.getLocale());
//        LocalDateTime firstDayOfWeek = timeSource.now().toLocalDateTime()
//                .withYear(event.getYear())
//                .with(weekFields.weekOfYear(), event.getWeek())
//                .with(weekFields.dayOfWeek(), 1);
//
//        calendarRange.setValue(CalendarMode.WEEK);
//        atDate(CalendarMode.WEEK, firstDayOfWeek.toLocalDate());
//        loadEvents();

        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("CUBAs Calendar.CalendarWeekClickEvent fired")
                .show();

    }




    @Subscribe("navigatorPrevious")
    protected void onNavigatorPreviousClick(Button.ClickEvent event) {
        CalendarMode calendarMode = calendarRange.getValue();
        previous(calendarMode);
        loadEvents();
    }

    @Subscribe("navigatorNext")
    protected void onNavigatorNextClick(Button.ClickEvent event) {
        CalendarMode calendarMode = calendarRange.getValue();
        next(calendarMode);
        loadEvents();
    }

    @Subscribe("navigatorCurrent")
    protected void onNavigatorCurrentClick(Button.ClickEvent event) {
        CalendarMode calendarMode = calendarRange.getValue();
        current(calendarMode);
        loadEvents();
    }

    @Subscribe("calendarRangePicker")
    protected void onCalendarRangePickerValueChange(HasValue.ValueChangeEvent<LocalDate> event) {
        if (event.isUserOriginated()) {
            CalendarMode calendarMode = calendarRange.getValue();
            atDate(calendarMode, event.getValue());
            loadEvents();
        }
    }

    private void current(CalendarMode calendarMode) {
        String description = calendarNavigators.forMode(calendarMode).atDate(calendar, calendarRangePicker, timeSource.now().toLocalDate());
        calendarRangeDescription.setValue(description);
    }

    private void atDate(CalendarMode calendarMode, LocalDate date) {
        String description = calendarNavigators.forMode(calendarMode).atDate(calendar, calendarRangePicker, date);
        calendarRangeDescription.setValue(description);
    }

    private void next(CalendarMode calendarMode) {
        String description = calendarNavigators.forMode(calendarMode).next(calendar, calendarRangePicker);
        calendarRangeDescription.setValue(description);
    }

    private void previous(CalendarMode calendarMode) {
        String description = calendarNavigators.forMode(calendarMode).previous(calendar, calendarRangePicker);
        calendarRangeDescription.setValue(description);
    }


    @Subscribe("calendarRange")
    protected void onCalendarRangeValueChange(HasValue.ValueChangeEvent event) {
        if (event.isUserOriginated()) {
            atDate((CalendarMode) event.getValue(), calendarRangePicker.getValue());
            loadEvents();
        }
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        typeMultiFilter.setValue(EnumSet.allOf(VisitType.class));
        calendarRange.setValue(CalendarMode.WEEK);
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