package com.haulmont.sample.petclinic.web.visit.visit.calendar.navigation;

import java.time.LocalDate;

public interface CalendarNavigation {
    void navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate);
}
