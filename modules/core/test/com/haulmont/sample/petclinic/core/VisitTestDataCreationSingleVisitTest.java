package com.haulmont.sample.petclinic.core;


import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FluentLoader;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.sample.petclinic.config.PetclinicTestdataConfig;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import com.haulmont.sample.petclinic.entity.visit.VisitTreatmentStatus;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VisitTestDataCreationSingleVisitTest {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);

    VisitTestDataCreation visitTestDataCreation;

    @Mock
    PetclinicTestdataConfig petclinicTestdataConfig;
    @Mock
    TimeSource timeSource;
    @Mock
    DataManager dataManager;

    PetclinicData data;


    @BeforeEach
    void setup() {

        lenient().when(dataManager.create(Visit.class))
                .then(invocation -> new Visit());
        lenient().when(timeSource.now())
                .thenReturn(ZonedDateTime.now());

        visitTestDataCreation = new VisitTestDataCreation(
            petclinicTestdataConfig,
            timeSource,
            dataManager,
            new RandomVisitDateTime()
        );

        data = new PetclinicData();
    }

    @Test
    void when_createVisit_thenVisitContainsRandomValuesForTypeAndPetAndDescriptionAndNurse() {

        // given:
        possibleDescriptions("Fever, Disease");

        List<Pet> possiblePets = asList(
                data.pet("Pikachu"),
                data.pet("Garchomp")
        );

        possiblePets(possiblePets);

        List<User> possibleNurses = asList(
            data.nurse("Joy"),
            data.nurse("Audino")
        );
        possibleNurses(possibleNurses);

        // when:
        Visit visit = visitTestDataCreation.createVisit(TOMORROW);

        // then:
        assertThat(visit.getType())
                .isNotNull();
        assertThat(visit.getPet())
                .isIn(possiblePets);
        assertThat(visit.getAssignedNurse())
            .isIn(possibleNurses);

        assertThat(visit.getDescription())
                .isIn("Fever", "Disease");
    }


    @Test
    void when_createVisitInTheFutureMoreThenOneWeek_thenAssignedNurseIsNotSet() {

        // given:
        possibleDescriptions("Fever, Disease");

        List<Pet> possiblePets = asList(
                data.pet("Pikachu"),
                data.pet("Garchomp")
        );

        possiblePets(possiblePets);

        possibleNurses(asList(
            data.nurse("Joy"),
            data.nurse("Audino")
        ));

        // when:
        Visit visit = visitTestDataCreation.createVisit(TOMORROW.plusDays(7));

        // then:
        assertThat(visit.getAssignedNurse())
            .isNull();;
    }



    @Test
    void when_createVisitForYesterday_thenTreatmentStatusIsDone() {

        // given:
        somePossibleDescriptions();
        somePossiblePets();
        somePossibleNurses();

        // when:
        Visit visit = visitTestDataCreation.createVisit(YESTERDAY);

        // then:
        assertThat(visit.getTreatmentStatus())
                .isEqualTo(VisitTreatmentStatus.DONE);
    }


    @Test
    void when_createVisitForToday_thenTreatmentStatusIsUpComing() {

        // given:
        somePossibleDescriptions();
        somePossiblePets();
        somePossibleNurses();

        // when:
        Visit visit = visitTestDataCreation.createVisit(TODAY);

        // then:
        assertThat(visit.getTreatmentStatus())
                .isEqualTo(VisitTreatmentStatus.IN_PROGRESS);
    }

    @Test
    void when_createVisitForTomorrow_thenTreatmentStatusIsUpComing() {

        // given:
        somePossibleDescriptions();
        somePossiblePets();
        somePossibleNurses();

        // when:
        Visit visit = visitTestDataCreation.createVisit(TOMORROW);

        // then:
        assertThat(visit.getTreatmentStatus())
                .isEqualTo(VisitTreatmentStatus.UPCOMING);
    }

    private void somePossibleNurses() {
        possibleNurses(asList(data.nurse("Joy")));
    }

    private void somePossibleDescriptions() {
        possibleDescriptions("Fever, Disease");
    }

    private void somePossiblePets() {
        possiblePets(asList(data.pet("Pikachu")));
    }

    private void possiblePets(List<Pet> possiblePets) {
        mockList(Pet.class, possiblePets);
    }

    private void possibleNurses(List<User> nurses) {

        FluentLoader fluentLoader = mock(FluentLoader.class);

        lenient().when(dataManager.load(Role.class))
            .thenReturn(fluentLoader);


        final List<UserRole> nursesUserRoles = nurses.stream()
            .map(user -> {
                final UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRoleName("Nurse");
                return userRole;
            })
            .collect(Collectors.toList());

        mockList(UserRole.class, nursesUserRoles);
    }
    private <E extends Entity> void mockList(Class<E> entityClass, List<E> entityList) {
        FluentLoader fluentLoader = mock(FluentLoader.class);

        lenient().when(dataManager.load(entityClass))
            .thenReturn(fluentLoader);

        lenient().when(fluentLoader.view(any(Consumer.class)))
            .thenReturn(fluentLoader);

        lenient().when(fluentLoader.list())
            .thenReturn(entityList);
    }


    private void possibleDescriptions(String descriptions) {
        lenient().when(petclinicTestdataConfig.getTestdataVisitDescriptionOptions())
                .thenReturn(descriptions);
    }

}