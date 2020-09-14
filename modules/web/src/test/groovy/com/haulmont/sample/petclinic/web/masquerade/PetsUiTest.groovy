package com.haulmont.sample.petclinic.web.masquerade

import com.codeborne.selenide.Selenide
import com.haulmont.masquerade.Conditions
import com.haulmont.masquerade.components.HasActions
import com.haulmont.masquerade.components.SideMenu
import com.haulmont.masquerade.components.Table
import com.haulmont.sample.petclinic.web.masquerade.ui.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import static com.haulmont.masquerade.Components.wire
import static com.haulmont.masquerade.Conditions.*
import static com.haulmont.masquerade.Selectors.$c
import static com.haulmont.masquerade.Selectors.byText
import static com.haulmont.masquerade.components.Table.SortDirection.DESCENDING

class PetsUiTest {

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
                nameField.setValue("Some specialist")
                saveBtn.click()
            }

            //create new Veterinarian

            $c(SideMenu).openItem('application-masterdata', 'petclinic_Veterinarian.browse')
            wire(VeterinarianBrowse).with {
                create.click()
                $c(VeterinarianEditor).with {
                    firstName.setValue("First")
                    lastName.setValue("Last")
                    add.click()
                    $c(Specialties).with {
                        table.selectRow(byText("Some specialist"))
                        selectButton.click()
                    }
                    ok.click()
                }
            }

            //create new Pet type

            $c(SideMenu).openItem('application-masterdata', "petclinic_PetType.browse")
            wire(PetTypes).with {
                createBtn.shouldBe(ENABLED) click()
                nameField.shouldBe(EDITABLE) setValue("Some Pet Type")
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
                    firstName.setValue("First")
                    lastName.setValue("Last")
                    address.setValue("address")
                    city.setValue("some city")
                    email.setValue('some@email.com')
                    ok.click()
                }

                //create new Pet

                $c(SideMenu).openItem('petclinic_Pet.browse')
                wire(PetBrowser).createBtn.click()
                wire(PetEditor).with {
                    name.setValue("Pet Name")
                    identificationNumber.setValue("123456789")
                    datepart.setDateValue("01012018")
                    ownerField.openOptionsPopup().select(byText('First Last'))
                    type.triggerAction(new HasActions.Action("picker_lookup"))
                    wire(PetTypes).with {
                        table.sort("column_name", DESCENDING)
                        table.selectRow(byText("Some Pet Type"))
                        select.click()
                    }
                    ok.click()
                }
            }
        }
    }
}


