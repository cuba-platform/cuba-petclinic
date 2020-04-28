package com.haulmont.sample.petclinic.web.screens.visit;

import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.gui.components.DatePicker;
import com.haulmont.cuba.gui.components.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Locale;

import static com.haulmont.sample.petclinic.web.screens.visit.CalendarNavigationMode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MonthCalendarNavigationTest {

    private static final LocalDate MARCH_1 = LocalDate.of(2020, 3, 1);
    private static final LocalDate MARCH_15 = LocalDate.of(2020, 3, 15);
    private static final LocalDate APR_15 = MARCH_15.plusMonths(1);
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
    DatePicker<LocalDate> calendarRangePicker;
    @Mock
    Label<String> calendarTitle;

    @BeforeEach
    void setUp() {
        sut = new MonthCalendarNavigation(
                CalendarScreenAdjustment.of(calendar, calendarRangePicker, calendarTitle),
                Locale.GERMANY
        );
    }

    @Test
    void when_atDate_then_captionContainsTheMonthNamePlusYear() {

        // when:
        sut.navigate(AT_DATE, LocalDate.of(2020, 3, 5));

        // then:
        calendarTitleIs("März 2020");
    }

    @Test
    void given_March1IsStartDate_when_previousMonth_then_calendarRangeIsFebruary_and_calendarPickerIsFeb1() {

        // when:
        sut.navigate(PREVIOUS, MARCH_1);

        // then:
        calendarStartIs(FEB_1.atStartOfDay());
        calendarEndIs(FEB_29.atTime(LocalTime.MAX));

        // and:
        calendarPickerIs(FEB_1);
    }

    @Test
    void given_currentDateIsApr30_when_currentMonth_then_calendarRangeIsApril_and_calendarPickerIsApr30() {

        // when:
        sut.navigate(AT_DATE, APR_30);

        // then:
        calendarStartIs(APR_1.atStartOfDay());
        calendarEndIs(APR_30.atTime(LocalTime.MAX));

        // and:
        calendarPickerIs(APR_30);
    }

    @Test
    void given_March1IsStartDate_when_next_then_calendarRangeIsApril_and_calendarPickerIsApr1() {

        // when:
        sut.navigate(NEXT, MARCH_1);

        // then:
        calendarStartIs(APR_1.atStartOfDay());
        calendarEndIs(APR_30.atTime(LocalTime.MAX));

        // and:
        calendarPickerIs(APR_1);
    }

    @Test
    void given_March15IsStartDate_when_next_then_calendarRangeIsApril_and_calendarPickerIsApr15() {

        // when:
        sut.navigate(NEXT, MARCH_15);

        // then:
        calendarStartIs(APR_1.atStartOfDay());
        calendarEndIs(APR_30.atTime(LocalTime.MAX));

        // and:
        calendarPickerIs(APR_15);
    }

    private void calendarEndIs(LocalDateTime expectedEnd) {
        verify(calendar).setEndDate(expectedEnd);
    }

    private void calendarStartIs(LocalDateTime expectedStart) {
        verify(calendar).setStartDate(expectedStart);
    }

    private void calendarPickerIs(LocalDate expectedDate) {
        verify(calendarRangePicker).setValue(expectedDate);
    }

    private void calendarTitleIs(String expectedCaption) {
        verify(calendarTitle).setValue(expectedCaption);
    }
}