package com.haulmont.sample.petclinic.service.visit;

import com.haulmont.cuba.core.app.EmailerAPI;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EmailInfo;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.pet.PetType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service(DiseaseWarningMailingService.NAME)
public class DiseaseWarningMailingServiceBean implements DiseaseWarningMailingService
     {

    @Inject
    protected DataManager dataManager;


    @Inject
    protected EmailerAPI emailerAPI;


    @Override
    public int warnAboutDisease(PetType petType, String disease, String city) {

        List<Pet> petsInDiseaseCity = findEndangeredPets(petType, city);

        petsInDiseaseCity.forEach(pet -> sendWarningForPet(pet, disease, city));

        return petsInDiseaseCity.size();
    }

    private List<Pet> findEndangeredPets(PetType petType, String city) {
        return dataManager.load(Pet.class)
                .query("select e from petclinic_Pet e where e.owner.city = :ownerCity and e.type = :petType")
                .parameter("ownerCity", city)
                .parameter("petType", petType)
                .view("pet-with-owner-and-type")
                .list();
    }

    private void sendWarningForPet(Pet pet, String disease, String city) {

        String emailSubject = "Warning about " + disease + " in the Area of " + city;

        Map<String, Serializable> templateParameters = new HashMap<>();

        templateParameters.put("owner", pet.getOwner());
        templateParameters.put("pet", pet);
        templateParameters.put("disease", disease);
        templateParameters.put("city", city);


        EmailInfo email = new EmailInfo(
                pet.getOwner().getEmail(),
                emailSubject,
                null,
                "com/haulmont/sample/petclinic/templates/disease-warning-mailing.txt",
                templateParameters
        );

        emailerAPI.sendEmailAsync(email);
    }
}