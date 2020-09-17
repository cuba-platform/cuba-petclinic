package com.haulmont.sample.petclinic.web.masquerade

import com.codeborne.selenide.Selenide
import com.haulmont.masquerade.components.DialogWindow
import com.haulmont.masquerade.components.HasActions
import com.haulmont.masquerade.components.SideMenu
import com.haulmont.masquerade.sys.TagNames
import com.haulmont.sample.petclinic.web.masquerade.ui.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Selenide.$
import static com.haulmont.masquerade.Conditions.*
import static com.haulmont.masquerade.Selectors.*
import static com.haulmont.masquerade.components.Table.SortDirection.ASCENDING
import static com.haulmont.masquerade.components.Table.SortDirection.DESCENDING
import static com.haulmont.sample.petclinic.web.masquerade.helper.UiTestHelper.setTimeFieldValue

class PetsUiTest {


    public static final String PET_TYPE = "Some Pet Type"
    public static final String PET_NAME = "Pet Name"
    public static final String OWNER_FIRST_NAME = "First"
    public static final String OWNER_LAST_NAME = "Last"
    public static final String OWNER_ADDRESS = "address"
    public static final String CITY = "some city"
    public static final String OWNER_PHONE = "89271234567"
    public static final String OWNER_EMAIL = 'some@email.com'
    public static final String SPECIALIST_NAME = "Some specialist"
    public static final String PET_ID = "123456789"
    public static final String OWNER_FULL_NAME = 'First Last'
    public static final String PICKER_LOOKUP = "picker_lookup"
    public static final String REGULAR_CHECKUP = "Regular Checkup"
    public static final String NURSE = "Nurse Joy"
    public static final String DESCRIPTION = "Some description"

    @AfterAll
    static void after() {
        Selenide.closeWebDriver()
    }

    @Test
    @DisplayName('Create a pet')
    void createPet() {

        Selenide.open("/petclinic/")

        //Login as admin
        $c(LoginWindow).login('admin', 'admin')

        //create new Speciality
        $c(SideMenu).openItem('application-masterdata', 'petclinic_Specialty.browse')
        $c(Specialties).with {
            createBtn.shouldBe(ENABLED).click()
            nameField.setValue(SPECIALIST_NAME)
            Thread.sleep(2000)
            saveBtn.click()
        }

        //create new Veterinarian
        $c(SideMenu).openItem('application-masterdata', 'petclinic_Veterinarian.browse')
        Thread.sleep(2000)
        $c(VeterinarianBrowse).create.shouldBe(ENABLED).click()

        $c(VeterinarianEditor).with {
            firstName.shouldBe(EDITABLE).setValue(OWNER_FIRST_NAME)
            lastName.setValue(OWNER_LAST_NAME)
            Thread.sleep(2000)
            add.shouldBe(ENABLED).click()
            $c(Specialties).with {
                Thread.sleep(2000)
                table.selectRow(byText(SPECIALIST_NAME))
                selectButton.click()
            }
            Thread.sleep(2000)
            ok.click()
        }

        //create new Pet type
        $c(SideMenu).openItem('application-masterdata', "petclinic_PetType.browse")
        $c(PetTypes).with {
            Thread.sleep(2000)
            createBtn.shouldBe(ENABLED).click()
            nameField.shouldBe(EDITABLE).setValue(PET_TYPE)
            Thread.sleep(2000)
            saveBtn.click()
        }
        $c(MainWindow).logout()
        Thread.sleep(2000)

        //login as joy
        $c(LoginWindow).defaultLogin()

        //create new Owner
        $c(SideMenu).openItem('petclinic_Owner.browse')
        Thread.sleep(2000)
        $c(Owners).createBtn.click()

        $c(OwnerEditor).with {
            Thread.sleep(2000)
            firstName.shouldBe(EDITABLE).setValue(OWNER_FIRST_NAME)
            lastName.shouldBe(EDITABLE).setValue(OWNER_LAST_NAME)
            address.shouldBe(EDITABLE).setValue(OWNER_ADDRESS)
            Thread.sleep(2000)
            city.shouldBe(EDITABLE).setValue(CITY)
            telephone.shouldBe(EDITABLE).setValue(OWNER_PHONE)
            email.setValue(OWNER_EMAIL)
            Thread.sleep(2000)
            ok.click()
        }

        //create new Pet
        $c(SideMenu).openItem('petclinic_Pet.browse')
        $c(PetBrowser).with {
            Thread.sleep(2000)
            createBtn.shouldBe(ENABLED).click()
            $c(PetEditor).with {
                Thread.sleep(2000)
                name.setValue(PET_NAME)
                identificationNumber.setValue(PET_ID)
                datepart.setDateValue("01012018")
                ownerField.openOptionsPopup().select(byText(OWNER_FULL_NAME))
                Thread.sleep(2000)
                type.triggerAction(new HasActions.Action(PICKER_LOOKUP))
                $c(PetTypes).with {
                    Thread.sleep(2000)
                    table.sort("column_name", DESCENDING)
                    table.selectRow(byText(PET_TYPE))
                    Thread.sleep(2000)
                    select.shouldBe(ENABLED).click()
                }
                Thread.sleep(2000)
                ok.shouldBe(ENABLED).click()
            }
            idFilterField.shouldBe(EDITABLE).setValue(PET_ID)
            ownerFilterField.openOptionsPopup().select(byText(OWNER_FULL_NAME))
            Thread.sleep(2000)
            petsTable.selectRow(byText(PET_TYPE)).shouldBe(VISIBLE)
        }

        //create visit for a pet
        $c(SideMenu).openItem('petclinic_Visit.browse')
        Thread.sleep(2000)
        $c(MainWindow).switchTab("tableTab", AllVisits).with {
            Thread.sleep(2000)
            createBtn.shouldBe(ENABLED).click()
            $c(VisitEditor).with {
                Thread.sleep(2000)
                pet.triggerAction(new HasActions.Action(PICKER_LOOKUP))
                $c(PetEditor).with {
                    Thread.sleep(2000)
                    idFilterField.shouldBe(EDITABLE).setValue(PET_ID)
                    typeFilterField.openOptionsPopup().select(byText(PET_TYPE))
                    petsTable.selectRow(byText(PET_NAME))
                    Thread.sleep(2000)
                    select.shouldBe(ENABLED).click()
                }
                Thread.sleep(2000)
                typeField.openOptionsPopup().select(byText(REGULAR_CHECKUP))
                assignedNurseField.openOptionsPopup().select(NURSE)
                description.setValue(DESCRIPTION)
                Thread.sleep(2000)
                datepartStart.setDateValue(getVisitDate())
                datepartEnd.setDateValue(getVisitDate())
                setTimeFieldValue('visitStart', '0600')
                setTimeFieldValue('visitEnd', '0630')
                Thread.sleep(2000)
                treatmentStatusField.setValue("Upcoming")
                Thread.sleep(2000)
                ok.shouldBe(ENABLED).click()
            }
        }
        $c(MainWindow).switchTab("calendarTab", CalendarTab).with {
            Thread.sleep(2000)
            calendarMode.select("1")
            Thread.sleep(2000)
            typeMultiFilter.select("Recharge")
            typeMultiFilter.select("Status Condition Healing")
            Thread.sleep(2000)
            typeMultiFilter.select("Disease Treatment")
            typeMultiFilter.select("Other")
            Thread.sleep(2000)

            //find created visit in calendar
            findCreatedVisitInCalendar("6:00 AM")
            Thread.sleep(2000)

            $c(VisitEditor).with {
                ok.shouldBe(ENABLED).click()
                Thread.sleep(2000)
            }


            //change status of created visit
            $c(SideMenu).openItem('myVisits')
            Thread.sleep(2000)
            $c(CurrentVisits).with {
                table.sort('column_visitStart', ASCENDING).selectRow(byIndex(0))
                Thread.sleep(2000)
                startBtn.click()
                Thread.sleep(2000)
                table.selectRow(byIndex(0)).shouldHave(text("In progress"))
                finishBtn.click()
                Thread.sleep(2000)
                $c(DialogWindow, 'dialog_petclinic_myVisits').close()
                Thread.sleep(2000)
            }

            //check that status of visit is Done
            $c(SideMenu).openItem('petclinic_Visit.browse')
            Thread.sleep(2000)
            $c(MainWindow).switchTab("calendarTab", CalendarTab).with {
                Thread.sleep(2000)
                calendarMode.select("1")
                Thread.sleep(2000)
                typeMultiFilter.select("Recharge")
                typeMultiFilter.select("Status Condition Healing")
                Thread.sleep(2000)
                typeMultiFilter.select("Disease Treatment")
                typeMultiFilter.select("Other")
                Thread.sleep(2000)
                findCreatedVisitInCalendar("6:00 AM")
                $c(VisitEditor).with {
                    Thread.sleep(2000)
                    treatmentStatusField.shouldHave(value("Done"))
                    Thread.sleep(2000)
                    cancel.shouldBe(ENABLED).click()
                }
            }
        }
    }

    static String getVisitDate() {
        def df = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        def date = LocalDate.now()
        date.format(df)
    }

    static void findCreatedVisitInCalendar(String label) {
        $(byChain(byCubaId('calendar'),
                byClassName('v-calendar-event-event-blue'),
                TagNames.DIV,
                TagNames.SPAN)).shouldBe(VISIBLE)
                .shouldHave(text(label))
                .click()
    }
}







