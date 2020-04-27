package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.RelativeDates.beginningOfMonth;
import static com.haulmont.sample.petclinic.web.visit.visit.calendar.RelativeDates.endOfMonth;

public class MonthCalendarNavigation implements CalendarNavigation {

    private Calendar<LocalDateTime> calendar;
    private final Locale locale;
    private DatePicker<LocalDate> calendarNavigator;

    public MonthCalendarNavigation(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarNavigator, Locale locale) {
        this.calendarNavigator = calendarNavigator;
        this.calendar = calendar;
        this.locale = locale;
    }

    @Override
    public String navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newMonthDate = navigationMode.calculate(ChronoUnit.MONTHS, referenceDate);
        YearMonth newMonth = YearMonth.from(newMonthDate);
        calendar.setStartDate(beginningOfMonth(newMonth));
        calendar.setEndDate(endOfMonth(newMonth));
        calendarNavigator.setValue(newMonthDate);
        return MonthFormatter.fullMonthYear(newMonth, locale);
    }
}
