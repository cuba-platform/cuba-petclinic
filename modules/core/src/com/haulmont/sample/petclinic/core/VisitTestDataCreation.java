package com.haulmont.sample.petclinic.core;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EntitySet;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.sample.petclinic.config.PetclinicTestdataConfig;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import com.haulmont.sample.petclinic.entity.visit.VisitTreatmentStatus;
import com.haulmont.sample.petclinic.entity.visit.VisitType;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.util.CollectionUtils;

@Component
public class VisitTestDataCreation {

    @Inject
    private Logger log;

    protected final PetclinicTestdataConfig petclinicTestdataConfig;
    protected final TimeSource timeSource;

    protected final DataManager dataManager;
    protected final RandomVisitDateTime randomVisitDateTime;

    public VisitTestDataCreation(
            PetclinicTestdataConfig petclinicTestdataConfig,
            TimeSource timeSource,
            DataManager dataManager,
            RandomVisitDateTime randomVisitDateTime
    ) {
        this.petclinicTestdataConfig = petclinicTestdataConfig;
        this.timeSource = timeSource;
        this.dataManager = dataManager;
        this.randomVisitDateTime = randomVisitDateTime;
    }

    protected void createData() {

        if (visitsExists()) {
            log.info("Visits found in DB. Visit Test data generation is skipped...");
            return;
        }

        log.info("No Visits found in the DB. Visit Test data will be created...");

        EntitySet visits = commit(createVisits());

        log.info(visits.size() + " Visits created");

    }

    List<Visit> createVisits() {
        return Stream.concat(
                createPastVisits(),
                createFutureVisits()
        )
                .collect(Collectors.toList());
    }

    private Stream<Visit> createPastVisits() {
        return IntStream.range(0, petclinicTestdataConfig.getTestdataVisitStartAmountPastDays())
                .mapToObj(value -> timeSource.now().minusDays(value).toLocalDate())
                .map(this::createVisitsForDate)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull);
    }

    private Stream<Visit> createFutureVisits() {
        return IntStream.range(1, petclinicTestdataConfig.getTestdataVisitStartAmountFutureDays() + 1)
                .mapToObj(i -> createVisitsForDate(
                        timeSource.now().plusDays(i).toLocalDate(),
                        amountForFutureDate(i)
                ))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull);
    }

    private int amountForFutureDate(int i) {
        int max = petclinicTestdataConfig.getTestdataVisitStartAmountFutureDays() + 1;
        return (int) ((double) (max - i) / max * petclinicTestdataConfig.getTestdataVisitAmountPerDay());
    }

    private List<Visit> createVisitsForDate(LocalDate localDate) {
        return IntStream.range(0, petclinicTestdataConfig.getTestdataVisitAmountPerDay())
                .mapToObj(i -> createVisit(localDate))
                .collect(Collectors.toList());
    }

    private List<Visit> createVisitsForDate(LocalDate localDate, int amount) {
        return IntStream.range(0, amount)
                .mapToObj(i -> createVisit(localDate))
                .collect(Collectors.toList());
    }

    private boolean visitsExists() {
        return list(Visit.class).size() > 0;
    }

    private EntitySet commit(List<Visit> visits) {

        Visit[] visitsToBePersisted = toArray(visits);
        return dataManager.commit(visitsToBePersisted);
    }

    private Visit[] toArray(List<Visit> visits) {
        return visits.toArray(new Visit[visits.size()]);
    }

    Visit createVisit(LocalDate date) {

        VisitEventRange visitEventRange = randomVisitDateTime.randomVisitEventRange(date);

        if (visitEventRange.isEmpty()) {
            return null;
        }

        Visit visit = dataManager.create(Visit.class);

        visit.setTreatmentStatus(treatmentStatusFor(date));
        visit.setAssignedNurse(randomNurse(date));
        visit.setPet(randomPet());
        visit.setType(randomVisitType());
        visit.setDescription(randomDescription());

        visit.setVisitStart(visitEventRange.getVisitStart());
        visit.setVisitEnd(visitEventRange.getVisitEnd());

        return visit;
    }

    private User randomNurse(LocalDate date) {

        if (nurseShouldBeAssigned(date)) {
            return null;
        }
        final List<User> possibleNurses = list(UserRole.class, viewBuilder -> {
            viewBuilder.add("roleName");
            viewBuilder.add("user", View.LOCAL);
        })
            .stream()
            .filter(userRole -> userRole.getRoleName().equals("Nurse"))
            .map(UserRole::getUser)
            .distinct()
            .collect(Collectors.toList());

        return randomOfList(possibleNurses);

    }

    private boolean nurseShouldBeAssigned(LocalDate date) {
        return date.isAfter(timeSource.now().toLocalDate().plusWeeks(1));
    }

    private VisitTreatmentStatus treatmentStatusFor(LocalDate date) {
        final LocalDate today = timeSource.now().toLocalDate();
        if (date.equals(today)) {
            return VisitTreatmentStatus.IN_PROGRESS;
        }
        else if (date.isAfter(today)) {
            return VisitTreatmentStatus.UPCOMING;
        }
        else {
            return VisitTreatmentStatus.DONE;
        }
    }

    private VisitType randomVisitType() {
        int pick = random().nextInt(VisitType.values().length);
        return VisitType.values()[pick];
    }

    private String randomDescription() {
        return randomOfList(
                Arrays.asList(petclinicTestdataConfig.getTestdataVisitDescriptionOptions().split(","))
        ).trim();
    }

    private Pet randomPet() {
        List<Pet> pets = list(Pet.class);
        return randomOfList(pets);
    }

    private <T> T randomOfList(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(random().nextInt(list.size()));
    }

    private Random random() {
        return new Random();
    }

    private <T extends Entity> List<T> list(Class<T> entityClass) {
        return dataManager.load(entityClass).list();
    }
    private <T extends Entity> List<T> list(Class<T> entityClass, Consumer<ViewBuilder> viewBuilderConfigurer) {
        return dataManager.load(entityClass).view(viewBuilderConfigurer).list();
    }

}