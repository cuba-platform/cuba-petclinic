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
import java.util.Locale;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeekCalendarNavigationTest {


    private static final LocalDate W1_MON = LocalDate.of(2020, 3, 30);
    private static final LocalDate W1_WED = W1_MON.plusDays(2);
    private static final LocalDateTime W1_MONDAY_MIDNIGHT = LocalDateTime.of(W1_MON, LocalTime.MIDNIGHT);
    private static final LocalDateTime W1_WED_NOON = LocalDateTime.of(W1_WED, LocalTime.NOON);
    private static final LocalDateTime W2_MONDAY_MIDNIGHT = W1_MONDAY_MIDNIGHT.plusWeeks(1);
    private static final LocalDateTime W1_SUNDAY_MAX = LocalDateTime.of(LocalDate.of(2020, 4, 5), LocalTime.MAX);
    private static final LocalDateTime W2_SUNDAY_MAX = W1_SUNDAY_MAX.plusWeeks(1);

    private WeekCalendarNavigation sut;

    @Mock
    Calendar<LocalDateTime> calendar;

    @BeforeEach
    void setUp() {
        sut = new WeekCalendarNavigation(timeSource, Locale.GERMANY);
    }

    @Mock
    TimeSource timeSource;

    @Test
    void given_week1MondayIsStartDate_when_nextMonth_then_calendarRangeIsW2MondayTillSundayMax() {

        // given:
        calenderIsCurrently(W1_MONDAY_MIDNIGHT);

        // when:
        sut.next(calendar);

        // then:
        calendarStartIs(W2_MONDAY_MIDNIGHT);
        calendarEndIs(W2_SUNDAY_MAX);
    }

    @Test
    void given_week2MondayIsStartDate_when_nextMonth_then_calendarRangeIsW1MondayTillSundayMax() {

        // given:
        calenderIsCurrently(W2_MONDAY_MIDNIGHT);

        // when:
        sut.previous(calendar);

        // then:
        calendarStartIs(W1_MONDAY_MIDNIGHT);
        calendarEndIs(W1_SUNDAY_MAX);
    }

    @Test
    void given_week1WedNoonIsStartDate_when_nextMonth_then_calendarRangeIsW2MondayTillSundayMax() {

        // given:
        calenderIsCurrently(W1_WED_NOON);

        // when:
        sut.next(calendar);

        // then:
        calendarStartIs(W2_MONDAY_MIDNIGHT);
        calendarEndIs(W2_SUNDAY_MAX);
    }

    @Test
    void given_currentDateIsWednesday_when_currentWeek_then_calendarRangeIsThisWeek() {

        todayIs(W1_WED_NOON);

        // when:
        sut.atDate(calendar);

        calendarStartIs(W1_MONDAY_MIDNIGHT);
        calendarEndIs(W1_SUNDAY_MAX);
    }

    private void todayIs(LocalDateTime current) {
        when(timeSource.now()).thenReturn(current.atZone(ZoneId.systemDefault()));
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
}