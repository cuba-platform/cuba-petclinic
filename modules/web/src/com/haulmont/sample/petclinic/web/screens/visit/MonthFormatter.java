package com.haulmont.sample.petclinic.web.screens.visit;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthFormatter {

    public static String fullMonth(YearMonth month, Locale locale) {
        return month.getMonth().getDisplayName(TextStyle.FULL, locale);
    }

    public static String fullMonthYear(YearMonth month, Locale locale) {
        return fullMonth(month, locale) + " " + month.getYear();
    }

    public static String shortMonth(YearMonth month, Locale locale) {
        return month.getMonth().getDisplayName(TextStyle.SHORT, locale);
    }
    public static String shortMonthYear(YearMonth month, Locale locale) {
        return shortMonth(month, locale) + " " + month.getYear();
    }

}
