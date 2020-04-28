package com.haulmont.sample.petclinic.web.screens.visit;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.function.BiFunction;

public enum CalendarNavigationMode {

    PREVIOUS(
            (unit, referenceDate) -> referenceDate.minus(1, unit)
    ),
    NEXT(
            (unit, referenceDate) -> referenceDate.plus(1, unit)
    ),
    AT_DATE(
            ((unit, referenceDate) -> referenceDate)
    );

    private final BiFunction<TemporalUnit, LocalDate, LocalDate> adjustmentFunction;

    CalendarNavigationMode(BiFunction<TemporalUnit, LocalDate, LocalDate> adjustmentFunction) {
        this.adjustmentFunction = adjustmentFunction;
    }

    public LocalDate calculate(TemporalUnit unit, LocalDate referenceDate) {
        return adjustmentFunction.apply(unit, referenceDate);
    }
}
