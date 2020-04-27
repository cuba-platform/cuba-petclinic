package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import java.time.LocalDate;

public interface CalendarNavigation {
    String navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate);
}
