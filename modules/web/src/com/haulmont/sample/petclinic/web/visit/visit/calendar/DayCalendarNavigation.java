package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DayCalendarNavigation implements CalendarNavigation<LocalDateTime, LocalDate> {

    public String previous(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker) {
        LocalDate prevDay = calendar.getStartDate().minusDays(1).toLocalDate();
        setDay(calendar, calendarRangePicker, prevDay);
        return prevDay.toString();
    }

    private void setDay(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate date) {
        calendar.setStartDate(date.atStartOfDay());
        calendar.setEndDate(date.atTime(LocalTime.MAX));
        calendarRangePicker.setValue(date);
    }

    public String next(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker) {
        LocalDate nextDay = calendar.getStartDate().plusDays(1).toLocalDate();
        setDay(calendar, calendarRangePicker, nextDay);
        return nextDay.toString();
    }

    @Override
    public String atDate(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate date) {
        setDay(calendar, calendarRangePicker, date);
        return date.toString();
    }

}
