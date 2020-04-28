package com.haulmont.sample.petclinic.web;

import java.time.LocalDate;

public class DateRange {

  private final LocalDate startDate;
  private final LocalDate endDate;

  public DateRange(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

}