package com.haulmont.sample.petclinic.web.screens.main;

import com.haulmont.cuba.gui.components.AbstractMainWindow;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.mainwindow.FtsField;

import javax.inject.Inject;
import java.util.Map;

public class ExtAppMainWindow extends AbstractMainWindow {
    @Inject
    private FtsField ftsField;

    @Inject
    private Embedded logoImage;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

//        initLayoutAnalyzerContextMenu(logoImage);
//        initLogoImage(logoImage);
//        initFtsField(ftsField);
    }
}