package com.haulmont.sample.petclinic.web.sneferu;

import com.haulmont.cuba.gui.components.RadioButtonGroup;
import de.diedavids.sneferu.components.descriptor.GenericComponentDescriptor;
import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;

public class RadioButtonGroupComponentDescriptor
        extends GenericComponentDescriptor<RadioButtonGroup, GenericComponentTestAPI<RadioButtonGroup>> {

    public RadioButtonGroupComponentDescriptor(String componentId) {
        super(RadioButtonGroup.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<RadioButtonGroup> createTestAPI(RadioButtonGroup component) {
        return new GenericComponentTestAPI<>(component);
    }
}