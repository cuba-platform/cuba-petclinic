package com.haulmont.sample.petclinic.web.visit.visit.calendar;

import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MonthCalendarNavigationTest {

    private static final LocalDate MARCH_1 = LocalDate.of(2020, 3, 1);
    private static final LocalDate FEB_1 = MARCH_1.minusMonths(1);
    private static final LocalDate FEB_29 = MARCH_1.minusDays(1);
    private static final LocalDate APR_1 = MARCH_1.plusMonths(1);
    private static final LocalDate APR_30 = YearMonth.from(APR_1).atEndOfMonth();
    private static final LocalDate TUE = MARCH_1.plusDays(1);
    private static final LocalDate WED = TUE.plusDays(1);
    private static final LocalDate THU = WED.plusDays(1);
    private static final LocalDate FRI = THU.plusDays(1);
    private static final LocalDate SAT = FRI.plusDays(1);
    private static final LocalDate SUN = SAT.plusDays(1);

    private MonthCalendarNavigation sut;

    @Mock
    Calendar<LocalDateTime> calendar;
    @Mock
    TimeSource timeSource;

    @BeforeEach
    void setUp() {
        sut = new MonthCalendarNavigation(timeSource);
    }

    @Test
    void given_March1IsStartDate_when_previousMonth_then_calendarRangeIsFebruary() {

        calenderIsCurrently(MARCH_1.atStartOfDay());

        // when:
        sut.previous(calendar);

        calendarStartIs(FEB_1.atStartOfDay());
        calendarEndIs(FEB_29.atTime(LocalTime.MAX));
    }

    @Test
    void given_currentDateIsApr30_when_currentMonth_then_calendarRangeIsJune() {

        todayIs(APR_30.atStartOfDay());

        // when:
        sut.atDate(calendar);

        calendarStartIs(APR_1.atStartOfDay());
        calendarEndIs(APR_30.atTime(LocalTime.MAX));
    }

    @Test
    void given_March1IsStartDate_when_nextMonth_then_calendarRangeIsApril() {

        calenderIsCurrently(MARCH_1.atStartOfDay());

        // when:
        sut.next(calendar);

        calendarStartIs(APR_1.atStartOfDay());
        calendarEndIs(APR_30.atTime(LocalTime.MAX));
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