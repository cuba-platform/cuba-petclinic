package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CalendarNavigation<T, R> {

    String previous(Calendar<T> calendar, DatePicker<R> calendarRangePicker);
    String next(Calendar<T> calendar, DatePicker<R> calendarRangePicker);
    String atDate(Calendar<T> calendar, DatePicker<R> calendarRangePicker, LocalDate date);
}
