package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.RelativeDates.*;

public class WeekCalendarNavigation implements CalendarNavigation {

    private final Locale locale;
    private final Calendar<LocalDateTime> calendar;
    private final DatePicker<LocalDate> calendarNavigator;

    public WeekCalendarNavigation(
            Calendar<LocalDateTime> calendar,
            DatePicker<LocalDate> calendarNavigator,
            Locale locale
    ) {
        this.calendar = calendar;
        this.calendarNavigator = calendarNavigator;
        this.locale = locale;
    }

    @Override
    public String navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newWeek = navigationMode.calculate(ChronoUnit.WEEKS, referenceDate);
        calendar.setStartDate(startOfWeek(newWeek, locale).atStartOfDay());
        calendar.setEndDate(endOfWeek(newWeek.atStartOfDay(), locale).atTime(LocalTime.MAX));
        calendarNavigator.setValue(newWeek);
        return newWeek.toString();
    }

}
