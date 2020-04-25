package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekCalendarNavigation implements CalendarNavigation<LocalDateTime, LocalDate> {


    private final Locale locale;

    public WeekCalendarNavigation(Locale locale) {
        this.locale = locale;
    }

    public String previous(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker) {
        LocalDate previousWeek = startOfWeek(calendar.getStartDate()).minus(1, ChronoUnit.WEEKS);
        setWeek(calendar, calendarRangePicker, previousWeek);
        return previousWeek.toString();
    }

    public String next(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker) {
        LocalDate nextWeek = startOfWeek(calendar.getStartDate()).plus(1, ChronoUnit.WEEKS);
        setWeek(calendar, calendarRangePicker, nextWeek);
        return nextWeek.toString();
    }

    @Override
    public String atDate(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate date) {
        LocalDate atDateWeek = startOfWeek(date.atTime(LocalTime.MIDNIGHT));
        setWeek(calendar, calendarRangePicker, atDateWeek);
        calendarRangePicker.setValue(date);
        return atDateWeek.toString();
    }

    private LocalDate startOfWeek(LocalDateTime date) {
        final DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        return date.with(TemporalAdjusters.previousOrSame(firstDayOfWeek)).toLocalDate();
    }

    private LocalDate endOfWeek(LocalDateTime date) {
        final DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
        return date.with(TemporalAdjusters.nextOrSame(lastDayOfWeek)).toLocalDate();
    }

    private void setWeek(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate referenceDate) {
        calendar.setStartDate(referenceDate.atStartOfDay());
        calendar.setEndDate(endOfWeek(referenceDate.atStartOfDay()).atTime(LocalTime.MAX));
        calendarRangePicker.setValue(referenceDate);
    }

}
