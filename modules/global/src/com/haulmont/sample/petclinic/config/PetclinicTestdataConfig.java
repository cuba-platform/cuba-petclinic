package com.haulmont.sample.petclinic.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;

@Source(type = SourceType.APP)
public interface PetclinicTestdataConfig extends Config {

    @Property("petclinic.testdata.visit.amount")
    Integer getTestdataVisitAmount();

    @Property("petclinic.testdata.visit.visitDate.rangeYears")
    Integer getTestdataVisitVisitDateRangeYears();

    @Property("petclinic.testdata.visit.description.options")
    String getTestdataVisitDescriptionOptions();
}