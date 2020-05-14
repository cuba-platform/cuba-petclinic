package com.haulmont.sample.petclinic.web.screens.main;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.Action.ActionPerformedEvent;
import com.haulmont.cuba.gui.components.Button.ClickEvent;
import com.haulmont.cuba.gui.components.Component.Alignment;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.web.app.login.LoginScreen;
import com.haulmont.cuba.web.gui.screen.ScreenDependencyUtils;
import com.vaadin.ui.Dependency.Type;
import javax.inject.Inject;


@Route(path = "login", root = true)
@UiController("login")
@UiDescriptor("petclinic-login-screen.xml")
public class PetclinicLoginScreen extends LoginScreen {

    @Inject
    protected HBoxLayout bottomPanel;

    @Inject
    protected Label<String> poweredByLink;
    @Inject
    protected MessageDialogFacet helpDialog;

    @Subscribe
    public void onAppLoginScreenInit(InitEvent event) {
        loadStyles();

        initBottomPanel();
    }

    @Subscribe("submit")
    public void onSubmit(ActionPerformedEvent event) {
        login();
    }

    protected void loadStyles() {
        ScreenDependencyUtils.addScreenDependency(this,
                "vaadin://brand-login-screen/login.css", Type.STYLESHEET);
    }

    protected void initBottomPanel() {
        if (!globalConfig.getLocaleSelectVisible()) {
            poweredByLink.setAlignment(Alignment.MIDDLE_CENTER);

            if (!webConfig.getLoginDialogPoweredByLinkVisible()) {
                bottomPanel.setVisible(false);
            }
        }
    }

    @Override
    protected void initLogoImage() {
        logoImage.setSource(RelativePathResource.class)
                .setPath("VAADIN/brand-login-screen/petclinic_logo_body.svg");
    }

    @Subscribe("helpBtn")
    protected void onHelpBtnClick(ClickEvent event) {
        helpDialog.show();
    }
}