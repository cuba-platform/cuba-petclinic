package com.haulmont.sample.petclinic.core;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EntitySet;
import com.haulmont.sample.petclinic.config.PetclinicTestdataConfig;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class VisitTestDataCreation {

    @Inject
    protected PetclinicTestdataConfig petclinicTestdataConfig;

    @Inject
    private Logger log;

    @Inject
    protected DataManager dataManager;

    protected void createData() {

        if (visitsExists()) {
            log.info("Visits found in DB. Visit Test data generation is skipped...");
            return;
        }

        log.info("No Visits found in the DB. Visit Test data will be created...");

        EntitySet visits = commit(createVisits());

        log.info(visits.size() + " Visits created");

    }

    private List<Visit> createVisits() {
        return IntStream.range(0, petclinicTestdataConfig.getTestdataVisitAmount())
                .mapToObj(i -> createVisit())
                .collect(Collectors.toList());
    }

    private boolean visitsExists() {
        return list(Visit.class).size() > 0;
    }

    private EntitySet commit(List<Visit> visits) {
        return dataManager.commit(toArray(visits));
    }

    private Visit[] toArray(List<Visit> visits) {
        return visits.toArray(new Visit[visits.size()]);
    }

    private Visit createVisit() {
        Visit visit = dataManager.create(Visit.class);

        List<Pet> pets = list(Pet.class);

        visit.setPet(randomPet(pets));
        visit.setVisitDate(randomDate());
        visit.setDescription(randomDescription());

        return visit;
    }

    private String randomDescription() {
        return randomOfList(
                Arrays.asList(petclinicTestdataConfig.getTestdataVisitDescriptionOptions().split(","))
        );
    }

    private Date randomDate() {
        return toDate(between(
                LocalDate.now().minusYears(petclinicTestdataConfig.getTestdataVisitVisitDateRangeYears()),
                LocalDate.now()
        ));
    }

    public Date toDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    private Pet randomPet(List<Pet> pets) {
        return randomOfList(pets);
    }

    private <T> T randomOfList(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    private <T extends Entity> List<T> list(Class<T> entityClass) {
        return dataManager.load(entityClass).list();
    }

}