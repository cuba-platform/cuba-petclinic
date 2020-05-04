package com.haulmont.sample.petclinic.web.screens.main;

import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.app.main.MainScreen;

import javax.inject.Inject;


@UiController("extMainScreen")
@UiDescriptor("ext-main-screen.xml")
public class ExtMainScreen extends MainScreen {
    @Inject
    protected SideMenu sideMenu;

    @Subscribe
    protected void onAfterShow1(AfterShowEvent event) {
        SideMenu.MenuItem blub = sideMenu.createMenuItem("blub");
        blub.setBadgeText("3 new");
        blub.setCaption("Blub");
        sideMenu.addMenuItem(blub, 0);
    }


}