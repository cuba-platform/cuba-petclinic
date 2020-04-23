package com.haulmont.sample.petclinic.core;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.*;

@Component
public class RandomVisitDateTime {


    public VisitEventRange randomVisitEventRange(LocalDate date) {

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return VisitEventRange.empty();
        }
        else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
            if (oneInNTimes(100)) {
                return VisitEventRange.empty();
            }
        }

        LocalDateTime visitStart = date.atTime(between(7,16));

        return VisitEventRange.of(visitStart, visitStart.plusMinutes(randomVisitLength()));
    }

    private boolean oneInNTimes(int n) {
        int randomOf100 = random().nextInt(n) + 1;
        return randomOf100 != n;
    }

    private static Random random() {
        return new Random();
    }

    private long randomVisitLength() {
        return (random().nextInt(3) + 1) * 30;
    }

    public static LocalTime between(int startTime, int endTime) {
        int hour = random().nextInt(endTime - startTime) + startTime;

        Integer minute = randomOfList(asList(0, 15, 30, 45));
        return LocalTime.of(hour, minute);
    }

    private static <T> T randomOfList(List<T> list) {
        return list.get(random().nextInt(list.size()));
    }
}