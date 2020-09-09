/*
 * Copyright (c) 2020 Haulmont. All rights reserved.
 */

package com.haulmont.sample.petclinic.web.masquerade.ui

import com.haulmont.masquerade.Wire
import com.haulmont.masquerade.base.Composite
import com.haulmont.masquerade.components.*

class LoginWindow extends Composite<LoginWindow> {
    @Wire
    public TextField loginField

    @Wire
    public PasswordField passwordField

    @Wire
    public CheckBox rememberMeCheckBox

    @Wire
    public Button loginButton

    @Wire
    public LookupField localesSelect

    @Wire
    public Label welcomeLabel

    MainWindow commit() {
        loginButton.click()

        new MainWindow()
    }

    MainWindow defaultLogin() {
        commit()
    }

    MainWindow login(String login, String password) {
        loginField.value = login
        passwordField.value = password

        commit()
    }
}
