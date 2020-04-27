package com.haulmont.sample.petclinic.web.visit.visit.calendar.navigation;


import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarMode;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component("petclinic_CalendarNavigators")
public class CalendarNavigators {

    @Inject
    protected UserSessionSource userSessionSource;

    public CalendarNavigation forMode(
            Calendar<LocalDateTime> calendar,
            DatePicker<LocalDate> calendarNavigator,
            Label<String> calendarTitle,
            DatatypeFormatter datatypeFormatter,
            CalendarMode mode
    ) {
        switch (mode) {
            case DAY: return new DayCalendarNavigation(calendar, calendarNavigator, calendarTitle, datatypeFormatter);
            case WEEK: return new WeekCalendarNavigation(calendar, calendarNavigator, calendarTitle, userSessionSource.getLocale());
            case MONTH: return new MonthCalendarNavigation(calendar, calendarNavigator, calendarTitle, userSessionSource.getLocale());
        }
        return null;
    }

}
