package com.cubaplatform.petclinic.service.visit;


import com.cubaplatform.petclinic.entity.visit.Visit;

public interface VisitService {
    String NAME = "petclinic_VisitService";

    public Visit createVisitForToday(String petId);
}