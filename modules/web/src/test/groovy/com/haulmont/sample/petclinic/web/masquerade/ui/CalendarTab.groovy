package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.OptionsGroup;

@Wire(path = 'calendarTab')
class CalendarTab extends Composite<CalendarTab>{
    @Wire
    public OptionsGroup calendarMode
    @Wire
    public OptionsGroup typeMultiFilter
}
