package com.haulmont.sample.petclinic.web.masquerade

import com.codeborne.selenide.Selenide
import com.haulmont.masquerade.components.HasActions
import com.haulmont.masquerade.components.SideMenu
import com.haulmont.sample.petclinic.web.masquerade.ui.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static com.haulmont.masquerade.Components.wire
import static com.haulmont.masquerade.Conditions.*
import static com.haulmont.masquerade.Selectors.$c
import static com.haulmont.masquerade.Selectors.byText
import static com.haulmont.masquerade.components.Table.SortDirection.DESCENDING

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
    public static final String NURSE = "Nurse Comfey"
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
        wire(LoginWindow).login('admin', 'admin')

        //create new Speciality

        wire(MainWindow).with {
            $c(SideMenu).openItem('application-masterdata', 'petclinic_Specialty.browse')
            wire(Specialties).with {
                createBtn.click()
                nameField.setValue(SPECIALIST_NAME)
                saveBtn.click()
            }

            //create new Veterinarian

            $c(SideMenu).openItem('application-masterdata', 'petclinic_Veterinarian.browse')
            wire(VeterinarianBrowse).with {
                create.shouldBe(ENABLED).click()
                $c(VeterinarianEditor).with {
                    firstName.shouldBe(EDITABLE).setValue(OWNER_FIRST_NAME)
                    lastName.setValue(OWNER_LAST_NAME)
                    add.shouldBe(ENABLED).click()
                    $c(Specialties).with {
                        table.selectRow(byText(SPECIALIST_NAME))
                        selectButton.click()
                    }
                    ok.click()
                }
            }

            //create new Pet type

            $c(SideMenu).openItem('application-masterdata', "petclinic_PetType.browse")
            wire(PetTypes).with {
                createBtn.shouldBe(ENABLED).click()
                nameField.shouldBe(EDITABLE).setValue(PET_TYPE)
                saveBtn.click()
            }
            logout()


            //login as joy

            wire(LoginWindow).defaultLogin()
            wire(MainWindow).with {

                //create new Owner

                $c(SideMenu).openItem('petclinic_Owner.browse')
                wire(Owners).createBtn.click()

                wire(OwnerEditor).with {
                    firstName.shouldBe(EDITABLE).setValue(OWNER_FIRST_NAME)
                    lastName.shouldBe(EDITABLE).setValue(OWNER_LAST_NAME)
                    address.shouldBe(EDITABLE).setValue(OWNER_ADDRESS)
                    city.shouldBe(EDITABLE).setValue(CITY)
                    telephone.shouldBe(EDITABLE).setValue(OWNER_PHONE)
                    email.setValue(OWNER_EMAIL)
                    ok.click()
                }

                //create new Pet

                $c(SideMenu).openItem('petclinic_Pet.browse')
                wire(PetBrowser).with {
                    createBtn.click()
                    wire(PetEditor).with {
                        name.setValue(PET_NAME)
                        identificationNumber.setValue(PET_ID)
                        datepart.setDateValue("01012018")
                        ownerField.openOptionsPopup().select(byText(OWNER_FULL_NAME))
                        type.triggerAction(new HasActions.Action(PICKER_LOOKUP))
                        wire(PetTypes).with {
                            table.sort("column_name", DESCENDING)
                            table.selectRow(byText(PET_TYPE))
                            select.shouldBe(ENABLED).click()
                        }
                        ok.shouldBe(ENABLED).click()
                    }
                    idFilterField.shouldBe(EDITABLE).setValue(PET_ID)
                    ownerFilterField.openOptionsPopup().select(byText(OWNER_FULL_NAME))
                    petsTable.selectRow(byText(PET_TYPE)).shouldBe(VISIBLE)
                }

                //create visit for a pet
                $c(SideMenu).openItem('petclinic_Visit.browse')
                switchTab("tableTab", AllVisits).with {
                    createBtn.shouldBe(ENABLED).click()
                    wire(VisitEditor).with {
                        pet.triggerAction(new HasActions.Action(PICKER_LOOKUP))
                        wire(PetEditor).with {
                            idFilterField.shouldBe(EDITABLE).setValue(PET_ID)
                            typeFilterField.openOptionsPopup().select(byText(PET_TYPE))
                            petsTable.selectRow(byText(PET_NAME))
                            select.shouldBe(ENABLED).click()
                        }
                        typeField.openOptionsPopup().select(byText(REGULAR_CHECKUP))
                        assignedNurseField.openOptionsPopup().select(NURSE)
                        description.setValue(DESCRIPTION)
                        datepartStart.setDateValue(getVisitDate())
                        datepartEnd.setDateValue(getVisitDate())
                        timepartStart.shouldBe(EDITABLE).setValue("1300")
                        timepartEnd.setValue("1400")
                        ok.shouldBe(ENABLED).click()
                    }
                }
                $c(SideMenu).openItem('myVisits')
            }
        }
    }

    static String getVisitDate() {
        def df = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        def date = LocalDate.now().plusDays(1)
        date.format(df)
    }

}


