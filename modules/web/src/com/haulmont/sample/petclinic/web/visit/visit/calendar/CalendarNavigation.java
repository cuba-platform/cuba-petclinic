package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;

public interface CalendarNavigation<T, R> {

    String previous(Calendar<T> calendar, DatePicker<R> calendarRangePicker, LocalDate referenceDate);
    String next(Calendar<T> calendar, DatePicker<R> calendarRangePicker, LocalDate referenceDate);
    String atDate(Calendar<T> calendar, DatePicker<R> calendarRangePicker, LocalDate referenceDate);
}
