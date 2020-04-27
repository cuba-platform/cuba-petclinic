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

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigationMode.*;

public class WeekCalendarNavigation implements CalendarNavigation {


    private final Locale locale;
    private final Calendar<LocalDateTime> calendar;
    private final DatePicker<LocalDate> calendarRangePicker;

    public WeekCalendarNavigation(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, Locale locale) {

        this.calendar = calendar;
        this.calendarRangePicker = calendarRangePicker;
        this.locale = locale;
    }

    @Override
    public String previous(LocalDate referenceDate) {
        return change(PREVIOUS, referenceDate);
    }

    @Override
    public String next(LocalDate referenceDate) {
        return change(NEXT, referenceDate);
    }

    @Override
    public String atDate(LocalDate referenceDate) {
        return change(AT_DATE, referenceDate);
    }

    private String change(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newWeek = navigationMode.calculate(ChronoUnit.WEEKS, referenceDate);
        calendar.setStartDate(startOfWeek(newWeek).atStartOfDay());
        calendar.setEndDate(endOfWeek(newWeek.atStartOfDay()).atTime(LocalTime.MAX));
        calendarRangePicker.setValue(newWeek);
        return newWeek.toString();
    }

    private LocalDate startOfWeek(LocalDate date) {
        final DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        return date.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
    }

    private LocalDate endOfWeek(LocalDateTime date) {
        final DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
        return date.with(TemporalAdjusters.nextOrSame(lastDayOfWeek)).toLocalDate();
    }

}
