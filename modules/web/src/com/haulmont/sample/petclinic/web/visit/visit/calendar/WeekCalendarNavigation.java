package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.MonthFormatter.*;
import static com.haulmont.sample.petclinic.web.visit.visit.calendar.MonthFormatter.fullMonthYear;
import static com.haulmont.sample.petclinic.web.visit.visit.calendar.MonthFormatter.shortMonthYear;
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
        LocalDate startOfWeek = startOfWeek(newWeek, locale);
        calendar.setStartDate(startOfWeek.atStartOfDay());
        LocalDate endOfWeek = endOfWeek(newWeek.atStartOfDay(), locale);
        calendar.setEndDate(endOfWeek.atTime(LocalTime.MAX));
        calendarNavigator.setValue(newWeek);
        return formatWeek(startOfWeek, endOfWeek);
    }

    private String formatWeek(LocalDate startOfWeek, LocalDate endOfWeek) {
        YearMonth start = YearMonth.from(startOfWeek);
        YearMonth end = YearMonth.from(endOfWeek);

        if (start.equals(end)) {
            return fullMonthYear(start, locale);
        }
        else if (startOfWeek.getYear() == endOfWeek.getYear()) {
            return shortMonth(start, locale) + " - " + shortMonthYear(end, locale);
        }
        else {
            return shortMonthYear(start, locale) + " - " + shortMonthYear(end, locale);
        }
    }


}
