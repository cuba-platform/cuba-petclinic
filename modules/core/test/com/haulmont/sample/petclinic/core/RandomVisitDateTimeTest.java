package com.haulmont.sample.petclinic.core;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class RandomVisitDateTimeTest {

    RandomVisitDateTime randomVisitDateTime;

    @BeforeEach
    void createTestEnvironment() {

        randomVisitDateTime = new RandomVisitDateTime();
    }

    @RepeatedTest(10)
    public void aVisitEventRangeStartsAt7AMEarliest() {

        // when:
        LocalDate date = LocalDate.now();
        VisitEventRange actual = randomVisitDateTime.randomVisitEventRange(date);

        // then:
        assertThat(actual.getVisitStart())
                .isAfter(date.atTime(7,0));
    }


    @RepeatedTest(1000)
    public void aVisitEventRangeEndsAt8PMLatest() {

        // when:
        LocalDate date = LocalDate.now();
        VisitEventRange actual = randomVisitDateTime.randomVisitEventRange(date);

        // then:
        assertThat(actual.getVisitEnd())
                .isBefore(date.atTime(18,0));
    }

    @RepeatedTest(1000)
    public void aVisitEventRangeIsMin15Minutes() {

        // when:
        LocalDate date = LocalDate.now();
        VisitEventRange actual = randomVisitDateTime.randomVisitEventRange(date);

        // then:
        assertThat(actual.lengthInMinutes())
                .isGreaterThanOrEqualTo(15);
    }


    @RepeatedTest(1000)
    public void aVisitEventRangeIsMax90Minutes() {

        // when:
        LocalDate date = LocalDate.now();
        VisitEventRange actual = randomVisitDateTime.randomVisitEventRange(date);

        // then:
        assertThat(actual.lengthInMinutes())
                .isLessThanOrEqualTo(90);
    }

    @RepeatedTest(100)
    public void aVisitEventStartsOnlyAtEveryFullQuarterHour() {

        // when:
        LocalDate date = LocalDate.now();
        VisitEventRange actual = randomVisitDateTime.randomVisitEventRange(date);

        // then:
        assertThat(actual.getVisitStart().getMinute() % 15)
            .isEqualTo(0);
    }

    @RepeatedTest(100)
    public void aVisitEventIsNeverGeneratedOnASunday() {

        // when:
        VisitEventRange actual = randomVisitDateTime.randomVisitEventRange(aSunday());

        // then:
        assertThat(actual)
                .isEqualTo(VisitEventRange.empty());
    }


    @RepeatedTest(1000)
    public void aVisitEventIsAlmostNeverGeneratedOnASaturday() {

        // when:
        List<Boolean> allResults = IntStream.range(0, 1000)
                .mapToObj(i -> randomVisitDateTime.randomVisitEventRange(aSaturday()))
                .map(VisitEventRange::isEmpty)
                .collect(Collectors.toList());

        long amountOfFilledSaturdays = allResults.stream()
                .filter(aBoolean -> !aBoolean)
                .count();

        // then:
        double filledToFreeRatio = (double) amountOfFilledSaturdays / (double) allResults.size();
        double ONE_IN_A_HUNDRED = 0.01;

        assertThat(filledToFreeRatio)
            .isCloseTo(ONE_IN_A_HUNDRED, within(0.03));
    }

    private LocalDate aSaturday() {
        return LocalDate.of(2020,1,4);
    }

    private LocalDate aSunday() {
        return LocalDate.of(2020,1,5);
    }

}