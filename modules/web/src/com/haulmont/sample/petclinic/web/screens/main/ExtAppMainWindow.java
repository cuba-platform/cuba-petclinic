package com.haulmont.sample.petclinic.web.screens.main;

import com.haulmont.cuba.gui.components.AbstractMainWindow;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.components.mainwindow.FtsField;
import java.util.Map;
import javax.inject.Inject;

public class ExtAppMainWindow extends AbstractMainWindow {
    @Inject
    private FtsField ftsField;

    @Inject
    private Image logoImage;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        initLayoutAnalyzerContextMenu(logoImage);
        initLogoImage(logoImage);
        initFtsField(ftsField);
    }
}