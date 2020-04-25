package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class MonthCalendarNavigation implements CalendarNavigation<LocalDateTime, LocalDate> {


    public String previous(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker) {

        YearMonth previousMonth = YearMonth.from(
                calendar.getStartDate().minus(1, ChronoUnit.MONTHS).toLocalDate()
        );
        setMonth(calendar,calendarRangePicker, previousMonth);
        return previousMonth.toString();
    }


    public String next(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker) {

        YearMonth nextMonth = YearMonth.from(
                calendar.getStartDate().plus(1, ChronoUnit.MONTHS).toLocalDate()
        );
        setMonth(calendar,calendarRangePicker, nextMonth);

        return nextMonth.toString();
    }

    @Override
    public String atDate(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, LocalDate date) {
        YearMonth atDateMonth = YearMonth.from(
                date
        );
        setMonth(calendar, calendarRangePicker, atDateMonth);
        calendarRangePicker.setValue(date);
        return atDateMonth.toString();
    }

    private void setMonth(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, YearMonth referenceMonth) {

        LocalDate referenceDate = referenceMonth.atDay(1);
        calendar.setStartDate(referenceDate.atStartOfDay());
        calendar.setEndDate(referenceMonth.atEndOfMonth().atTime(LocalTime.MAX));
        calendarRangePicker.setValue(referenceDate);
    }

}
