package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class MonthCalendarNavigation implements CalendarNavigation<LocalDateTime, LocalDate> {


    public String previous(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate referenceDate) {

        YearMonth previousMonth = YearMonth.from(
                referenceDate.minus(1, ChronoUnit.MONTHS)
        );
        setMonth(calendar,calendarRangePicker, previousMonth);
        return previousMonth.toString();
    }


    public String next(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate referenceDate) {

        YearMonth nextMonth = YearMonth.from(
                referenceDate.plus(1, ChronoUnit.MONTHS)
        );
        setMonth(calendar,calendarRangePicker, nextMonth);

        return nextMonth.toString();
    }

    @Override
    public String atDate(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate referenceDate) {
        YearMonth atDateMonth = YearMonth.from(
                referenceDate
        );
        setMonth(calendar, calendarRangePicker, atDateMonth);
        calendarRangePicker.setValue(referenceDate);
        return atDateMonth.toString();
    }

    private void setMonth(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, YearMonth referenceMonth) {

        LocalDate referenceDate = referenceMonth.atDay(1);
        calendar.setStartDate(referenceDate.atStartOfDay());
        calendar.setEndDate(referenceMonth.atEndOfMonth().atTime(LocalTime.MAX));
        calendarRangePicker.setValue(referenceDate);
    }

}
