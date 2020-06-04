package com.haulmont.sample.petclinic.web.screens.visit;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class RelativeDates {


    public static LocalDate startOfWeek(int year, int week, Locale locale) {
        WeekFields weekFields = WeekFields.of(locale);
        return LocalDateTime.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1)
                .toLocalDate();
    }

    public static LocalDate startOfWeek(LocalDate referenceDate, Locale locale) {
        final DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        return referenceDate.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
    }

    public static LocalDate endOfWeek(LocalDate referenceDate, Locale locale) {
        final DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
        return referenceDate.with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
    }

    public static LocalDate endOfMonth(YearMonth newMonth) {
        return newMonth.atEndOfMonth();
    }

    public static LocalDate beginningOfMonth(YearMonth newMonth) {
        return newMonth.atDay(1);
    }
}
