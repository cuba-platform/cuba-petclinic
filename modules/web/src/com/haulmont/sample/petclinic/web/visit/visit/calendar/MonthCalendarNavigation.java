package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.RelativeDates.beginningOfMonth;
import static com.haulmont.sample.petclinic.web.visit.visit.calendar.RelativeDates.endOfMonth;

public class MonthCalendarNavigation implements CalendarNavigation {

    private Calendar<LocalDateTime> calendar;
    private DatePicker<LocalDate> calendarNavigator;

    public MonthCalendarNavigation(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarNavigator) {
        this.calendarNavigator = calendarNavigator;
        this.calendar = calendar;
    }

    @Override
    public String navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newMonthDate = navigationMode.calculate(ChronoUnit.MONTHS, referenceDate);
        YearMonth newMonth = YearMonth.from(newMonthDate);
        calendar.setStartDate(beginningOfMonth(newMonth));
        calendar.setEndDate(endOfMonth(newMonth));
        calendarNavigator.setValue(newMonthDate);
        return newMonth.toString();
    }



}
