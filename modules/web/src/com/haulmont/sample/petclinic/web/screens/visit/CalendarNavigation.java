package com.haulmont.sample.petclinic.web.screens.visit;

import java.time.LocalDate;

public interface CalendarNavigation {
    void navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate);
}
