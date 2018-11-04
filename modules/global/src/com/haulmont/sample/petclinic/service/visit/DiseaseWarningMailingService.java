package com.haulmont.sample.petclinic.service.visit;

import com.haulmont.sample.petclinic.entity.pet.PetType;

public interface DiseaseWarningMailingService {
    String NAME = "petclinic_DiseaseWarningMailingService";

    int warnAboutDisease(PetType petType, String disease, String city);
}