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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
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

    /**
     * NOW is freezed to some wednesday in order to not accidentally hit the weekend limitation
     * of the visit test data generation
     */
    private static final LocalDate TODAY = LocalDate.now().with(DayOfWeek.WEDNESDAY);
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);
    private static final ZonedDateTime NOW = TODAY.atStartOfDay(ZoneId.of("Europe/Berlin")).plusHours(8);

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
                .thenReturn(NOW);

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

        List<User> possibleNurses = asList(
            data.nurse("Joy"),
            data.nurse("Audino")
        );

        // when:
        Visit visit = visitTestDataCreation.createVisit(TOMORROW, possiblePets, possibleNurses);

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

        // when:
        Visit visit = visitTestDataCreation.createVisit(
            TOMORROW.plusDays(7),
            asList(data.pet("Pikachu"), data.pet("Garchomp")),
            asList(data.nurse("Joy"), data.nurse("Audino"))
        );

        // then:
        assertThat(visit.getAssignedNurse())
            .isNull();
    }



    @Test
    void when_createVisitForYesterday_thenTreatmentStatusIsDone() {

        // given:
        somePossibleDescriptions();

        // when:
        Visit visit = visitTestDataCreation.createVisit(
            YESTERDAY,
            asList(data.pet("Pikachu")),
            asList(data.nurse("Joy"))
        );

        // then:
        assertThat(visit.getTreatmentStatus())
                .isEqualTo(VisitTreatmentStatus.DONE);
    }


    @Test
    void when_createVisitForToday_thenTreatmentStatusIsUpComing() {

        // given:
        somePossibleDescriptions();

        // when:
        Visit visit = visitTestDataCreation.createVisit(
            TODAY,
            asList(data.pet("Pikachu")),
            asList(data.nurse("Joy"))
        );

        // then:
        assertThat(visit.getTreatmentStatus())
                .isEqualTo(VisitTreatmentStatus.IN_PROGRESS);
    }

    @Test
    void when_createVisitForTomorrow_thenTreatmentStatusIsUpComing() {

        // given:
        somePossibleDescriptions();

        // when:
        Visit visit = visitTestDataCreation.createVisit(
            TOMORROW,
            asList(data.pet("Pikachu")),
            asList(data.nurse("Joy"))
        );

        // then:
        assertThat(visit.getTreatmentStatus())
                .isEqualTo(VisitTreatmentStatus.UPCOMING);
    }

    private void somePossibleDescriptions() {
        possibleDescriptions("Fever, Disease");
    }

    private void possibleDescriptions(String descriptions) {
        lenient().when(petclinicTestdataConfig.getTestdataVisitDescriptionOptions())
                .thenReturn(descriptions);
    }

}