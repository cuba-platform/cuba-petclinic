package com.cubaplatform.petclinic.service.visit;

import com.cubaplatform.petclinic.entity.pet.Pet;
import com.cubaplatform.petclinic.entity.visit.Visit;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.TimeSource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service(VisitService.NAME)
public class VisitServiceBean implements VisitService {


    @Inject
    protected DataManager dataManager;

    @Inject
    protected TimeSource timeSource;

    @Override
    public Visit createVisitForToday(String identificationNumber) {

        Pet pet = loadPetByIdentificationNumber(identificationNumber);

        return createVisitForPet(pet);
    }

    private Visit createVisitForPet(Pet pet) {

        Visit visit = dataManager.create(Visit.class);

        visit.setPet(pet);
        visit.setVisitDate(timeSource.currentTimestamp());

        return dataManager.commit(visit);
    }


    /**
     * loads a Pet by its Identification Number
     * @param identificationNumber the Identification Number to load
     * @return the Pet for the given Identification Number
     */
    private Pet loadPetByIdentificationNumber(String identificationNumber) {
        return dataManager.load(Pet.class)
                .query("select e from petclinic_Pet e where e.identificationNumber = :identificationNumber")
                .parameter("identificationNumber", identificationNumber)
                .one();
    }
}