package com.haulmont.sample.petclinic.web.sneferu;

import com.haulmont.cuba.gui.components.DatePicker;
import de.diedavids.sneferu.components.descriptor.GenericComponentDescriptor;
import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;

public class DatePickerComponentDescriptor
        extends GenericComponentDescriptor<DatePicker, GenericComponentTestAPI<DatePicker>> {

    public DatePickerComponentDescriptor(String componentId) {
        super(DatePicker.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<DatePicker> createTestAPI(DatePicker component) {
        return new GenericComponentTestAPI<>(component);
    }
}

