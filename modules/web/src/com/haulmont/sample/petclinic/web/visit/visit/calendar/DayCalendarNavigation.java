package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DayCalendarNavigation implements CalendarNavigation<LocalDateTime, LocalDate> {

    public String previous(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate referenceDate) {
        LocalDate prevDay = referenceDate.minusDays(1);
        setDay(calendar, calendarRangePicker, prevDay);
        return prevDay.toString();
    }

    private void setDay(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate date) {
        calendar.setStartDate(date.atStartOfDay());
        calendar.setEndDate(date.atTime(LocalTime.MAX));
        calendarRangePicker.setValue(date);
    }

    public String next(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate referenceDate) {
        LocalDate nextDay = referenceDate.plusDays(1);
        setDay(calendar, calendarRangePicker, nextDay);
        return nextDay.toString();
    }

    @Override
    public String atDate(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate referenceDate) {
        setDay(calendar, calendarRangePicker, referenceDate);
        return referenceDate.toString();
    }

}
