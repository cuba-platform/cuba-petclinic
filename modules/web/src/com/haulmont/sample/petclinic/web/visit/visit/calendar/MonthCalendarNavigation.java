package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import static com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarNavigationMode.*;

public class MonthCalendarNavigation implements CalendarNavigation {

    private Calendar<LocalDateTime> calendar;
    private DatePicker<LocalDate> calendarRangePicker;

    public MonthCalendarNavigation(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker) {
        this.calendarRangePicker = calendarRangePicker;
        this.calendar = calendar;
    }

    @Override
    public String previous(LocalDate referenceDate) {
        return changeWithRangePickerDateChange(PREVIOUS, referenceDate);
    }

    @Override
    public String next(LocalDate referenceDate) {
        return changeWithRangePickerDateChange(NEXT, referenceDate);
    }

    @Override
    public String atDate(LocalDate referenceDate) {
        return changeWithRangePickerDateChange(AT_DATE, referenceDate);
    }

    private String changeWithRangePickerDateChange(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newMonthDate = navigationMode.calculate(ChronoUnit.MONTHS, referenceDate);
        YearMonth newMonth = YearMonth.from(newMonthDate);
        calendar.setStartDate(newMonth.atDay(1).atStartOfDay());
        calendar.setEndDate(newMonth.atEndOfMonth().atTime(LocalTime.MAX));
        calendarRangePicker.setValue(newMonthDate);
        return newMonth.toString();
    }
}
