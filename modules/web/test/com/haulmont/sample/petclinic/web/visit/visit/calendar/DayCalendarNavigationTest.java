package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DayCalendarNavigationTest {
    private static final LocalDate MON = LocalDate.of(2020, 3, 30);
    private static final LocalDate TUE = MON.plusDays(1);
    private static final LocalDate WED = TUE.plusDays(1);
    private static final LocalDate THU = WED.plusDays(1);
    private static final LocalDate FRI = THU.plusDays(1);
    private static final LocalDate SAT = FRI.plusDays(1);
    private static final LocalDate SUN = SAT.plusDays(1);


    private static final LocalDateTime WED_NOON = LocalDateTime.of(WED, LocalTime.NOON);
    private static final LocalDateTime TUE_MIDNIGHT = LocalDateTime.of(TUE, LocalTime.MIDNIGHT);
    private static final LocalDateTime TUE_MAX = LocalDateTime.of(TUE, LocalTime.MAX);
    private static final LocalDateTime THU_MIDNIGHT = LocalDateTime.of(THU, LocalTime.MIDNIGHT);
    private static final LocalDateTime THU_MAX = LocalDateTime.of(THU, LocalTime.MAX);
    private DayCalendarNavigation sut;

    @Mock
    Calendar<LocalDateTime> calendar;

    @Mock
    TimeSource timeSource;

    @BeforeEach
    void setUp() {
        sut = new DayCalendarNavigation(timeSource);
    }

    @Test
    void given_wednesdayIsCurrentlyConfiguredAsStartDate_when_PreviousDay_then_calendarRangeIsTuesday() {

        calenderIsCurrently(WED_NOON);

        // when:
        sut.previous(calendar);

        calendarStartIs(TUE_MIDNIGHT);
        calendarEndIs(TUE_MAX);
    }

    @Test
    void given_wednesdayIsCurrentlyConfiguredAsStartDate_when_NextDay_then_calendarRangeIsThursday() {

        calenderIsCurrently(WED_NOON);

        // when:
        sut.next(calendar);

        calendarStartIs(THU_MIDNIGHT);
        calendarEndIs(THU_MAX);
    }


    @Test
    void given_currentDateIsThursday_when_currentDay_then_calendarRangeIsThursday() {

        todayIs(THU.atStartOfDay());

        // when:
        sut.atDate(calendar);

        calendarStartIs(THU.atStartOfDay());
        calendarEndIs(THU.atTime(LocalTime.MAX));
    }

    private void calendarEndIs(LocalDateTime expectedEnd) {
        verify(calendar).setEndDate(expectedEnd);
    }

    private void calendarStartIs(LocalDateTime expectedStart) {
        verify(calendar).setStartDate(expectedStart);
    }

    private void calenderIsCurrently(LocalDateTime current) {
        when(calendar.getStartDate()).thenReturn(current);
    }
    private void todayIs(LocalDateTime current) {
        when(timeSource.now()).thenReturn(current.atZone(ZoneId.systemDefault()));
    }
}