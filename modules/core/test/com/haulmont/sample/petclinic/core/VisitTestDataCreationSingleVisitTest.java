package com.haulmont.sample.petclinic.core;


import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FluentLoader;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.sample.petclinic.config.PetclinicTestdataConfig;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitTestDataCreationSingleVisitTest {

    VisitTestDataCreation visitTestDataCreation;

    @Mock
    PetclinicTestdataConfig petclinicTestdataConfig;

    @Mock
    TimeSource timeSource;
    private RandomVisitDateTime randomVisitDateTime;
    @Mock
    private DataManager dataManager;
    private PetclinicData data;

    private static final LocalDateTime TOMORROW_MORNING = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
    private static final LocalDate TOMORROW = TOMORROW_MORNING.toLocalDate();


    @BeforeEach
    void setup() {

        when(dataManager.create(Visit.class))
                .then(invocation -> new Visit());

        randomVisitDateTime = new RandomVisitDateTime();
        visitTestDataCreation = new VisitTestDataCreation(petclinicTestdataConfig, timeSource, dataManager, randomVisitDateTime);

        data = new PetclinicData();
    }

    @Test
    void given_noDaysInFutureShouldBeGenerated_when_generateVisits_then_noVisitIsInFuture() {

        // given:

        possibleDescriptions("Fever, Disease");

        List<Pet> possiblePets = asList(
                data.pet("Pikachu"),
                data.pet("Garchomp")
        );

        possiblePets(possiblePets);

        // when:
        Visit visit = visitTestDataCreation.createVisit(TOMORROW);
        // then:
        assertThat(visit.getType())
                .isNotNull();
        assertThat(visit.getPet())
                .isIn(possiblePets);

        assertThat(visit.getDescription())
                .isIn("Fever", "Disease");
    }

    private void possiblePets(List<Pet> pets) {
        FluentLoader petLoaderMock = mock(FluentLoader.class);
        when(dataManager.load(Pet.class))
                .thenReturn(petLoaderMock);

        when(petLoaderMock.list())
                .thenReturn(pets);
    }

    private void daysInPastToGenerateFor(int days) {
        when(petclinicTestdataConfig.getTestdataVisitStartAmountPastDays())
                .thenReturn(days);
    }

    private void daysInFutureToGenerateFor(int days) {
        when(petclinicTestdataConfig.getTestdataVisitStartAmountFutureDays())
                .thenReturn(days);
    }

    private void visitAmountPerDay(int amount) {
        when(petclinicTestdataConfig.getTestdataVisitAmountPerDay())
                .thenReturn(amount);
    }

    private void possibleDescriptions(String descriptions) {
        when(petclinicTestdataConfig.getTestdataVisitDescriptionOptions())
                .thenReturn(descriptions);
    }

}