package com.cubaplatform.petclinic.service.visit;


import com.cubaplatform.petclinic.entity.pet.PetType;

public interface DiseaseWarningMailingService {
    String NAME = "petclinic_DiseaseWarningMailingService";

    int warnAboutDisease(PetType petType, String disease, String city);
}