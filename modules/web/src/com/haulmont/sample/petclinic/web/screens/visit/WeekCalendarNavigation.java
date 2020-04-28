package com.haulmont.sample.petclinic.web.screens.visit;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.haulmont.sample.petclinic.web.screens.visit.MonthFormatter.*;
import static com.haulmont.sample.petclinic.web.screens.visit.RelativeDates.endOfWeek;
import static com.haulmont.sample.petclinic.web.screens.visit.RelativeDates.startOfWeek;

public class WeekCalendarNavigation implements CalendarNavigation {

    private final Locale locale;
    private final CalendarScreenAdjustment screenAdjustment;

    public WeekCalendarNavigation(
            CalendarScreenAdjustment screenAdjustment,
            Locale locale
    ) {
        this.screenAdjustment = screenAdjustment;
        this.locale = locale;
    }

    @Override
    public void navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newWeek = navigationMode.calculate(ChronoUnit.WEEKS, referenceDate);
        LocalDate startOfWeek = startOfWeek(newWeek, locale);
        LocalDate endOfWeek = endOfWeek(newWeek, locale);

        screenAdjustment.adjust(
                startOfWeek,
                endOfWeek,
                newWeek,
                formatTitle(startOfWeek, endOfWeek)
        );
    }

    private String formatTitle(LocalDate startOfWeek, LocalDate endOfWeek) {
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
