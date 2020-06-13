package com.haulmont.sample.petclinic.web.screens.main;

import com.haulmont.addon.helium.web.theme.HeliumThemeVariantsManager;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Security;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action.ActionPerformedEvent;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Timer.TimerActionEvent;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu.MenuItem;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.icons.Icons;
import com.haulmont.cuba.gui.icons.Icons.Icon;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.app.main.MainScreen;
import com.haulmont.sample.petclinic.entity.visit.Visit;
import com.haulmont.sample.petclinic.web.screens.visit.MyVisits;

import com.vaadin.server.Page;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.inject.Inject;


@UiController("petclinicMainScreen")
@UiDescriptor("petclinic-main-screen.xml")
public class PetclinicMainScreen extends MainScreen {

    @Inject
    protected SideMenu sideMenu;
    @Inject
    protected DataManager dataManager;
    @Inject
    protected UserSession userSession;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected HeliumThemeVariantsManager heliumThemeVariantsManager;
    @Inject
    protected Button switchThemeModeBtn;
    @Inject
    protected MessageBundle messageBundle;

    @Subscribe
    protected void initMainMenu(AfterShowEvent event) {
        createMyVisitMenuItem();
        openPetclinicMenuItem();

        final HeliumThemeSwitchBtnMode currentThemeMode = HeliumThemeSwitchBtnMode
            .fromId(heliumThemeVariantsManager.loadUserAppThemeModeSettingOrDefault());
        updateHeliumSwitchBtn(currentThemeMode);

        initMenuIcons();

    }

    private void initMenuIcons() {
        Stream.of(SideMenuIcon.values())
            .forEach(sideMenuIcon -> {

                final MenuItem menuItem = sideMenu.getMenuItem(sideMenuIcon.getMenuId());

                if (menuItem != null) {
                    menuItem.setIcon(sideMenuIcon.source());
                }

            });
    }

    private void updateHeliumSwitchBtn(HeliumThemeSwitchBtnMode mode) {
        switchThemeModeBtn.setIconFromSet(mode.getIcon());
        switchThemeModeBtn.setStyleName(mode.getStyleName());
    }


    private void openPetclinicMenuItem() {
        final MenuItem petclinicMenu = sideMenu.getMenuItem("application-petclinic");
        final MenuItem menuItem = petclinicMenu.getChildren().get(1);
        petclinicMenu.setExpanded(true);
        sideMenu.setSelectOnClick(true);
        sideMenu.setSelectedItem(menuItem);
    }

    private void createMyVisitMenuItem() {
        MenuItem myVisits = sideMenu.createMenuItem("myVisits");
        myVisits.setBadgeText(messageBundle.formatMessage("myVisitMenuItemBadge", amountOfVisits()));
        myVisits.setCaption(messageBundle.getMessage("myVisitsMenuItem"));
        myVisits.setIcon(CubaIcon.USER_CIRCLE.source());
        myVisits.setCommand(menuItem ->
            screenBuilders.screen(this)
                .withScreenClass(MyVisits.class)
                .withOpenMode(OpenMode.DIALOG)
                .show()
        );
        sideMenu.addMenuItem(myVisits, 0);
    }

    private int amountOfVisits() {
        return dataManager.load(Visit.class)
            .query(
                "e.assignedNurse = :currentUser and e.treatmentStatus <> @enum(com.haulmont.sample.petclinic.entity.visit.VisitTreatmentStatus.DONE)")
            .parameter("currentUser", userSession.getCurrentOrSubstitutedUser())
            .list().size();
    }

    @Subscribe("refreshMyVisits")
    protected void onRefreshMyVisitsTimerAction(TimerActionEvent event) {
        sideMenu.getMenuItem("myVisits")
            .setBadgeText(amountOfVisits() + " Visits");
    }

    @Subscribe("switchThemeMode")
    protected void onSwitchThemeMode(ActionPerformedEvent event) {

        final HeliumThemeSwitchBtnMode newTargetThemeMode = newTargetThemeMode();

        heliumThemeVariantsManager.setUserAppThemeMode(newTargetThemeMode.getName());
        Page.getCurrent().reload();
        updateHeliumSwitchBtn(newTargetThemeMode);
    }

    private HeliumThemeSwitchBtnMode newTargetThemeMode() {

        return HeliumThemeSwitchBtnMode.fromId(
            heliumThemeVariantsManager
                .getAppThemeModeList()
                .stream()
                .filter(mode -> !mode.equals(
                    heliumThemeVariantsManager.loadUserAppThemeModeSettingOrDefault())
                )
                .findFirst()
                .orElse("light")
        );
    }


}