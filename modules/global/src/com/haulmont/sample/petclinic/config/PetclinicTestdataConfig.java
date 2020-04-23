package com.haulmont.sample.petclinic.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;

@Source(type = SourceType.APP)
public interface PetclinicTestdataConfig extends Config {

    @Property("petclinic.testdata.visit.visitStart.amountPastDays")
    Integer getTestdataVisitStartAmountPastDays();

    @Property("petclinic.testdata.visit.visitStart.amountFutureDays")
    Integer getTestdataVisitStartAmountFutureDays();

    @Property("petclinic.testdata.visit.amountPerDay")
    Integer getTestdataVisitAmountPerDay();

    @Property("petclinic.testdata.visit.description.options")
    String getTestdataVisitDescriptionOptions();
}