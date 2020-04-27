package com.haulmont.sample.petclinic.web.visit.visit.calendar;


import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component("petclinic_CalendarNavigators")
public class CalendarNavigators {

    @Inject
    protected UserSessionSource userSessionSource;

    public CalendarNavigation forMode(Calendar<LocalDateTime> calendar, DatePicker<LocalDate> calendarRangePicker, CalendarMode mode) {
        switch (mode) {
            case DAY: return new DayCalendarNavigation(calendar, calendarRangePicker);
            case WEEK: return new WeekCalendarNavigation(calendar, calendarRangePicker, userSessionSource.getLocale());
            case MONTH: return new MonthCalendarNavigation(calendar, calendarRangePicker);
        }
        return null;
    }

}
