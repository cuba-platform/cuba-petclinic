package com.haulmont.sample.petclinic.web.visit.visit.calendar.navigation;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.MonthFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.MonthFormatter.*;
import static com.haulmont.sample.petclinic.web.visit.visit.calendar.RelativeDates.beginningOfMonth;
import static com.haulmont.sample.petclinic.web.visit.visit.calendar.RelativeDates.endOfMonth;

public class MonthCalendarNavigation implements CalendarNavigation {

    private Calendar<LocalDateTime> calendar;
    private final Label<String> calendarTitle;
    private final Locale locale;
    private DatePicker<LocalDate> calendarNavigator;

    public MonthCalendarNavigation(
            Calendar<LocalDateTime> calendar,
            DatePicker<LocalDate> calendarNavigator,
            Label<String> calendarTitle,
            Locale locale
    ) {
        this.calendarNavigator = calendarNavigator;
        this.calendar = calendar;
        this.calendarTitle = calendarTitle;
        this.locale = locale;
    }

    @Override
    public void navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newMonthDate = navigationMode.calculate(ChronoUnit.MONTHS, referenceDate);
        YearMonth newMonth = YearMonth.from(newMonthDate);
        calendar.setStartDate(beginningOfMonth(newMonth));
        calendar.setEndDate(endOfMonth(newMonth));
        calendarNavigator.setValue(newMonthDate);
        calendarTitle.setValue(fullMonthYear(newMonth, locale));
    }
}
