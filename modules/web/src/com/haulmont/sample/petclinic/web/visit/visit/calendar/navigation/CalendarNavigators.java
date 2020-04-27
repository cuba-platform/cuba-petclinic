package com.haulmont.sample.petclinic.web.visit.visit.calendar.navigation;


import com.haulmont.cuba.core.global.DatatypeFormatter;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.sample.petclinic.web.visit.visit.calendar.CalendarMode;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("petclinic_CalendarNavigators")
public class CalendarNavigators {

    @Inject
    protected UserSessionSource userSessionSource;

    public CalendarNavigation forMode(
            CalendarScreenAdjustment screenAdjustment,
            DatatypeFormatter datatypeFormatter,
            CalendarMode mode
    ) {
        switch (mode) {
            case DAY: return new DayCalendarNavigation(screenAdjustment, datatypeFormatter);
            case WEEK: return new WeekCalendarNavigation(screenAdjustment, userSessionSource.getLocale());
            case MONTH: return new MonthCalendarNavigation(screenAdjustment, userSessionSource.getLocale());
        }
        return null;
    }

}
