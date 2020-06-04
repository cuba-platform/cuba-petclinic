package com.haulmont.sample.petclinic.core;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class VisitEventRange {

    LocalDateTime visitStart;
    LocalDateTime visitEnd;

    private VisitEventRange(LocalDateTime visitStart, LocalDateTime visitEnd) {
        this.visitStart = visitStart;
        this.visitEnd = visitEnd;
    }

    public static VisitEventRange of(LocalDateTime visitStart, LocalDateTime visitEnd) {
        return new VisitEventRange(visitStart, visitEnd);
    }

    public static VisitEventRange empty() {
        return new VisitEventRange(null, null);
    }

    public LocalDateTime getVisitStart() {
        return visitStart;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public LocalDateTime getVisitEnd() {
        return visitEnd;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }

    public long lengthInMinutes() {
        return getVisitStart().until( getVisitEnd(), ChronoUnit.MINUTES );
    }

    public boolean isEmpty() {
        return getVisitStart() == null && getVisitEnd() == null;
    }

    @Override
    public String toString() {
        return "VisitEventRange{" +
                "visitStart=" + visitStart +
                ", visitEnd=" + visitEnd +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitEventRange that = (VisitEventRange) o;
        return Objects.equals(visitStart, that.visitStart) &&
                Objects.equals(visitEnd, that.visitEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitStart, visitEnd);
    }
}
