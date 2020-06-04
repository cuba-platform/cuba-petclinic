package com.haulmont.sample.petclinic.web.screens.visit;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Route;
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
import com.vaadin.v7.shared.ui.calendar.CalendarState;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.time.*;
import java.util.*;

import static com.haulmont.sample.petclinic.web.screens.visit.CalendarNavigationMode.*;
import static com.haulmont.sample.petclinic.web.screens.visit.CalendarNavigationMode.PREVIOUS;
import static com.haulmont.sample.petclinic.web.screens.visit.RelativeDates.*;

@UiController("petclinic_Visit.browse")
@UiDescriptor("visit-browse.xml")
@LookupComponent("visitsTable")
@LoadDataBeforeShow
@Route("visits")
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
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected Messages messages;

    @Subscribe
    protected void onInit(InitEvent event) {
        initTypeFilter();
        initSortCalendarEventsInMonthlyView();
    }

    private void initTypeFilter() {
        typeMultiFilter.setOptionsEnum(VisitType.class);
        typeMultiFilter.setValue(EnumSet.allOf(VisitType.class));
        typeMultiFilter.setOptionIconProvider(o -> VisitTypeIcon.valueOf(o.getIcon()).source());
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        current(CalendarMode.WEEK);
    }

    @SuppressWarnings("deprecation")
    private void initSortCalendarEventsInMonthlyView() {
        calendar.unwrap(com.vaadin.v7.ui.Calendar.class)
                .setEventSortOrder(CalendarState.EventSortOrder.START_DATE_DESC);
    }


    @Subscribe("calendar")
    protected void onCalendarCalendarDayClick(Calendar.CalendarDateClickEvent<LocalDateTime> event) {
        atDate(
            CalendarMode.DAY,
            event.getDate().toLocalDate()
        );
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarWeekClick(Calendar.CalendarWeekClickEvent<LocalDateTime> event) {
        atDate(
            CalendarMode.WEEK,
            startOfWeek(
                event.getYear(),
                event.getWeek(),
                userSessionSource.getLocale()
            )
        );
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

        calendarNavigators
                .forMode(
                        CalendarScreenAdjustment.of(calendar, calendarNavigator, calendarTitle),
                        datatypeFormatter,
                        calendarMode
                )
                .navigate(navigationMode, referenceDate);

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
    protected void onCalendarCalendarDayClick(Calendar.CalendarDayClickEvent<LocalDateTime> event) {
        Screen visitEditor = screenBuilders.editor(Visit.class, this)
                .newEntity()
                .withInitializer(visit -> {
                    visit.setVisitStart(event.getDate());
                    visit.setVisitEnd(event.getDate().plusHours(1));
                })
                .withOpenMode(OpenMode.DIALOG)
                .build();

        visitEditor.addAfterCloseListener(afterCloseEvent -> {
            if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                getScreenData().loadAll();
            }
        });

        visitEditor.show();
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
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(
                        messageBundle.formatMessage(
                                "visitUpdated",
                                messages.getMessage(visit.getType()),
                                visit.getPetName()
                        )
                )
                .show();
    }


}