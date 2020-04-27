package com.haulmont.sample.petclinic.web.visit.visit.calendar;


import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigationMode.*;

public class DayCalendarNavigation implements CalendarNavigation {

    private final Calendar<LocalDateTime> calendar;
    private final DatePicker<LocalDate> calendarRangePicker;

    public DayCalendarNavigation(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker) {
        this.calendar = calendar;
        this.calendarRangePicker = calendarRangePicker;
    }

    public String previous(LocalDate referenceDate) {
        return change(PREVIOUS, referenceDate);
    }

    public String next(LocalDate referenceDate) {
        return change(NEXT, referenceDate);
    }

    private String change(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newDate = navigationMode.calculate(ChronoUnit.DAYS, referenceDate);
        calendar.setStartDate(newDate.atStartOfDay());
        calendar.setEndDate(newDate.atTime(LocalTime.MAX));
        calendarRangePicker.setValue(newDate);
        return newDate.toString();
    }

    @Override
    public String atDate(LocalDate referenceDate) {
        return change(AT_DATE, referenceDate);
    }
}
