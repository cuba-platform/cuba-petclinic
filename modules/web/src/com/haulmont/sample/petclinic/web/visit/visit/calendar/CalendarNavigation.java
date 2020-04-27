package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import java.time.LocalDate;

public interface CalendarNavigation {
    String previous(LocalDate referenceDate);
    String next(LocalDate referenceDate);
    String atDate(LocalDate referenceDate);
}
