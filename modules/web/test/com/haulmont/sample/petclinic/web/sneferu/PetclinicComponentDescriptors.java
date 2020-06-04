package com.haulmont.sample.petclinic.web.sneferu;


import com.haulmont.sample.petclinic.web.sneferu.RadioButtonGroupComponentDescriptor;

public class PetclinicComponentDescriptors {

    /**
     * creates a {@link DatePickerComponentDescriptor} instance
     * @param id the id of the component as defined in the screen XML descriptor
     * @return a CalendarComponentDescriptor instance
     */
    public static DatePickerComponentDescriptor datePicker(String id) {
        return new DatePickerComponentDescriptor(id);
    }


    public static RadioButtonGroupComponentDescriptor radioButtonGroup(String id) {
        return new RadioButtonGroupComponentDescriptor(id);
    }


}
