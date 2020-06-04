package com.haulmont.sample.petclinic.web.screens.visit;


import com.haulmont.cuba.core.global.DatatypeFormatter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DayCalendarNavigation implements CalendarNavigation {

    private final DatatypeFormatter datatypeFormatter;
    private final CalendarScreenAdjustment screenAdjustment;

    public DayCalendarNavigation(
            CalendarScreenAdjustment screenAdjustment,
            DatatypeFormatter datatypeFormatter
    ) {
        this.screenAdjustment = screenAdjustment;
        this.datatypeFormatter = datatypeFormatter;
    }

    @Override
    public void navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newDate = navigationMode.calculate(ChronoUnit.DAYS, referenceDate);
        screenAdjustment.adjust(
                newDate,
                newDate,
                newDate,
                datatypeFormatter.formatLocalDate(newDate)
        );
    }
}
