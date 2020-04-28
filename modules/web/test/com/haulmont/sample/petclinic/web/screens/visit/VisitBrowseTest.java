package com.haulmont.sample.petclinic.web.screens.visit;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.gui.components.Calendar;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.haulmont.cuba.web.testsupport.proxy.DataServiceProxy;
import com.haulmont.cuba.web.testsupport.proxy.TestServiceProxy;
import com.haulmont.sample.petclinic.entity.owner.Owner;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import com.haulmont.sample.petclinic.web.PetclinicWebTestContainer;
import de.diedavids.sneferu.environment.SneferuTestUiEnvironment;
import de.diedavids.sneferu.environment.StartScreen;
import de.diedavids.sneferu.screen.StandardLookupTestAPI;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.haulmont.sample.petclinic.web.sneferu.PetclinicComponentDescriptors.datePicker;
import static com.haulmont.sample.petclinic.web.sneferu.PetclinicComponentDescriptors.radioButtonGroup;
import static de.diedavids.sneferu.ComponentDescriptors.calendar;
import static de.diedavids.sneferu.Interactions.select;
import static org.assertj.core.api.Assertions.assertThat;

class VisitBrowseTest {

    @RegisterExtension
    SneferuTestUiEnvironment environment =
            new SneferuTestUiEnvironment(PetclinicWebTestContainer.Common.INSTANCE)
                    .withScreenPackages(
                            "com.haulmont.cuba.web.app.main",
                            "com.haulmont.sample.petclinic.web"
                    )
                    .withUserLogin("admin")
                    .withMainScreen(MainScreen.class);

    private DataService dataService;
    private PetclinicData data;
    private Owner ash;
    private Owner misty;
    private List<Owner> ashAndMisty;

    @BeforeEach
    void setUp() {
        mockDataService();

        data = new PetclinicData(environment.getContainer());

    }

    private void mockDataService() {
        dataService = Mockito.spy(
                new DataServiceProxy(environment.getContainer())
        );
        TestServiceProxy.mock(DataService.class, dataService);
    }

    @Ignore
    @Test
    void when_selectingLastWeekInTheCalendarNavigator_then_theCalendarComponentUpdatesToLastWeek(
            @StartScreen StandardLookupTestAPI<Visit, VisitBrowse> screen
    ) {

        LocalDate MON = LocalDate.of(2020, 4, 27);
        LocalDate SUN = MON.plusDays(6);
        LocalDate TUE = MON.plusDays(1);

        screen
                .interact(select(radioButtonGroup("calendarMode"), CalendarMode.WEEK))
                .andThen(select(datePicker("calendarNavigator"), TUE));

        Calendar<LocalDateTime> calendar = screen.rawComponent(calendar("calendar"));

        assertThat(calendar.getEndDate().toLocalDate())
                .isEqualTo(SUN);
        assertThat(calendar.getStartDate().toLocalDate())
                .isEqualTo(MON);
    }

}
