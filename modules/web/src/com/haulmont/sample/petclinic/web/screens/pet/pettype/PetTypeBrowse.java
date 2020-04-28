package com.haulmont.sample.petclinic.web.screens.pet.pettype;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.ColorPicker;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.sample.petclinic.entity.pet.PetType;

import javax.inject.Inject;

import static com.haulmont.sample.petclinic.web.screens.pet.pettype.ColorGeneration.randomColor;

@UiController("petclinic_PetType.browse")
@UiDescriptor("pet-type-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
@Route("pet-types")
public class PetTypeBrowse extends MasterDetailScreen<PetType> {

    @Inject
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInitEntity(InitEntityEvent<PetType> event) {
        event.getEntity().setColor(randomColor());
    }

    @Install(to = "table.color", subject = "columnGenerator")
    protected Component tableColorColumnGenerator(PetType petType) {
        if (petType.getColor() != null) {
            return colorPicker(petType.getColor());
        }

        return null;
    }


    private Component colorPicker(String color) {
        ColorPicker component = uiComponents.create(ColorPicker.class);
        component.setValue(color);
        component.setEditable(false);
        return component;
    }


}