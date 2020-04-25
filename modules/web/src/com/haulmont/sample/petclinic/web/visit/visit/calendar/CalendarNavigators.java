package com.haulmont.sample.petclinic.web.visit.visit.calendar;


import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("petclinic_CalendarNavigators")
public class CalendarNavigators {

    @Inject
    protected UserSessionSource userSessionSource;

    public CalendarNavigation forMode(CalendarMode mode) {
        switch (mode) {
            case DAY: return new DayCalendarNavigation();
            case WEEK: return new WeekCalendarNavigation(userSessionSource.getLocale());
            case MONTH: return new MonthCalendarNavigation();
        }
        return null;
    }

}
