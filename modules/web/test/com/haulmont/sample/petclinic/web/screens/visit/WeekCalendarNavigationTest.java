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
import java.util.Locale;

import static com.haulmont.sample.petclinic.web.screens.visit.CalendarNavigationMode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeekCalendarNavigationTest {


    private static final LocalDate W1_MON = LocalDate.of(2020, 3, 30);
    private static final LocalDate W1_SUN = LocalDate.of(2020, 4, 5);
    private static final LocalDate W2_MON = W1_MON.plusWeeks(1);
    private static final LocalDate W1_WED = W1_MON.plusDays(2);
    private static final LocalDate W2_WED = W1_WED.plusWeeks(1);
    private static final LocalDateTime W1_MONDAY_MIDNIGHT = LocalDateTime.of(W1_MON, LocalTime.MIDNIGHT);
    private static final LocalDateTime W1_WED_NOON = LocalDateTime.of(W1_WED, LocalTime.NOON);
    private static final LocalDateTime W2_MONDAY_MIDNIGHT = W1_MONDAY_MIDNIGHT.plusWeeks(1);
    private static final LocalDateTime W1_SUNDAY_MAX = LocalDateTime.of(W1_SUN, LocalTime.MAX);
    private static final LocalDateTime W2_SUNDAY_MAX = W1_SUNDAY_MAX.plusWeeks(1);

    private WeekCalendarNavigation sut;

    @Mock
    Calendar<LocalDateTime> calendar;
    @Mock
    DatePicker<LocalDate> calendarRangePicker;
    @Mock
    Label<String> calendarTitle;


    @BeforeEach
    void setUp() {
        sut = new WeekCalendarNavigation(
                CalendarScreenAdjustment.of(calendar, calendarRangePicker, calendarTitle),
                Locale.GERMANY
        );
    }

    @Test
    void given_weekIsWithinAMonth_when_atDate_then_captionContainsTheMonthNamePlusYear() {

        // when:
        sut.navigate(AT_DATE, LocalDate.of(2020, 3, 5));

        // then:
        calendarTitleIs("März 2020");
    }

    @Test
    void given_weekOverlapsOverTheMonth_when_atDate_then_captionContainsBothMonthNames() {

        // when:
        sut.navigate(AT_DATE, LocalDate.of(2020, 3, 31));

        // then:
        calendarTitleIs("Mär - Apr 2020");
    }


    @Test
    void given_weekOverlapsOverTheYear_when_atDate_then_captionContainsBothMonthNamesAndYearNumbers() {

        // when:
        sut.navigate(AT_DATE, LocalDate.of(2020, 12, 31));

        // then:
        calendarTitleIs("Dez 2020 - Jan 2021");
    }

    @Test
    void given_week1MondayIsStartDate_when_next_then_calendarRangeIsW2MondayTillSundayMax_and_calendarPickerIsW2Mon() {

        // when:
        sut.navigate(NEXT, W1_MON);

        // then:
        calendarStartIs(W2_MONDAY_MIDNIGHT);
        calendarEndIs(W2_SUNDAY_MAX);

        // and:
        calendarPickerIs(W2_MON);
    }

    @Test
    void given_week2MondayIsStartDate_when_previous_then_calendarRangeIsW1MondayTillSundayMax_and_calendarPickerIsW1Mon() {

        // when:
        sut.navigate(PREVIOUS, W2_MON);

        // then:
        calendarStartIs(W1_MONDAY_MIDNIGHT);
        calendarEndIs(W1_SUNDAY_MAX);

        // and:
        calendarPickerIs(W1_MON);
    }

    @Test
    void given_week1WedNoonIsStartDate_when_next_then_calendarRangeIsW2MondayTillSundayMax_and_calendarPickerIsW2Wed() {

        // when:
        sut.navigate(NEXT, W1_WED);

        // then:
        calendarStartIs(W2_MONDAY_MIDNIGHT);
        calendarEndIs(W2_SUNDAY_MAX);

        // and:
        calendarPickerIs(W2_WED);
    }

    @Test
    void given_currentDateIsWednesday_when_atDate_then_calendarRangeIsThisWeek_and_calendarPickerIsW1Wed() {

        // when:
        sut.navigate(AT_DATE, W1_WED);

        calendarStartIs(W1_MONDAY_MIDNIGHT);
        calendarEndIs(W1_SUNDAY_MAX);

        // and:
        calendarPickerIs(W1_WED);
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