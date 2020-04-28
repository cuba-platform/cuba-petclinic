package com.haulmont.sample.petclinic.web.screens.visit;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;
import com.haulmont.cuba.gui.components.Label;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CalendarScreenAdjustment {
    private Calendar<LocalDateTime> calendar;
    private DatePicker<LocalDate> calendarNavigator;
    private Label<String> calendarTitle;

    public static CalendarScreenAdjustment of(
            Calendar<LocalDateTime> calendar,
            DatePicker<LocalDate> calendarNavigator,
            Label<String> calendarTitle
    ) {
        return new CalendarScreenAdjustment(calendar, calendarNavigator, calendarTitle);
    }

    private CalendarScreenAdjustment(
            Calendar<LocalDateTime> calendar,
            DatePicker<LocalDate> calendarNavigator,
            Label<String> calendarTitle
    ) {
        this.calendar = calendar;
        this.calendarNavigator = calendarNavigator;
        this.calendarTitle = calendarTitle;
    }

    public void adjust(LocalDate calendarStart, LocalDate calendarEnd, LocalDate navigatorDate, String title) {
        calendar.setStartDate(calendarStart.atStartOfDay());
        calendar.setEndDate(calendarEnd.atTime(LocalTime.MAX));
        calendarNavigator.setValue(navigatorDate);
        calendarTitle.setValue(title);
    }
}
