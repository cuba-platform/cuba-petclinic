package com.haulmont.sample.petclinic.web.pet.pet;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.sample.petclinic.entity.pet.Pet;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("petclinic_Pet.browse")
@UiDescriptor("pet-browse.xml")
@LookupComponent("petsTable")
@LoadDataBeforeShow
public class PetBrowse extends StandardLookup<Pet> {

    @Inject
    protected Notifications notifications;

    @Inject
    protected Metadata metadata;

    @Inject
    protected GroupTable<Pet> petsTable;

    @Subscribe("petsTable.calculateDiscount")
    public void calculateDiscount(Action.ActionPerformedEvent actionPerformedEvent) {

        Pet pet = petsTable.getSingleSelected();

        int discount = calculateDiscount(pet);

        showDiscountCalculatedNotification(pet, discount);
    }


    private void showDiscountCalculatedNotification(Pet pet, int discount) {

        String petName = metadata.getTools().getInstanceName(pet);

        String discountMessage = "Discount for " + petName + ": " + discount + "%";

        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(discountMessage)
                .show();
    }

    private int calculateDiscount(Pet pet) {
        int discount = 0;

        int visitAmount = pet.getVisits().size();
        if (visitAmount > 5) {
            discount = 5;
        } else if (visitAmount > 2) {
            discount = 2;
        }
        return discount;
    }
}