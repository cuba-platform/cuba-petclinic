package com.haulmont.sample.petclinic.web.visit.visit.calendar.navigation;


import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;
import com.haulmont.cuba.gui.components.Label;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DayCalendarNavigation implements CalendarNavigation {

    private final Calendar<LocalDateTime> calendar;
    private final DatePicker<LocalDate> calendarNavigator;
    private final Label<String> calendarTitle;
    private final DatatypeFormatter datatypeFormatter;

    public DayCalendarNavigation(
            Calendar<LocalDateTime> calendar,
            DatePicker<LocalDate> calendarNavigator,
            Label<String> calendarTitle,
            DatatypeFormatter datatypeFormatter
    ) {
        this.calendar = calendar;
        this.calendarNavigator = calendarNavigator;
        this.calendarTitle = calendarTitle;
        this.datatypeFormatter = datatypeFormatter;
    }

    @Override
    public void navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newDate = navigationMode.calculate(ChronoUnit.DAYS, referenceDate);
        calendar.setStartDate(newDate.atStartOfDay());
        calendar.setEndDate(newDate.atTime(LocalTime.MAX));
        calendarNavigator.setValue(newDate);
        calendarTitle.setValue(datatypeFormatter.formatLocalDate(newDate));
    }
}
