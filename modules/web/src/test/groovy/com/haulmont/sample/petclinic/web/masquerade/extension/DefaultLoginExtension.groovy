package com.haulmont.sample.petclinic.web.masquerade.extension

import com.codeborne.selenide.Selenide
import com.haulmont.sample.petclinic.web.masquerade.ui.LoginWindow
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

import static com.haulmont.masquerade.Components.wire

class DefaultLoginExtension implements BeforeEachCallback, AfterEachCallback {
    @Override
    void beforeEach(ExtensionContext context) throws Exception {
        Selenide.open("/petclinic/")
        wire(LoginWindow).defaultLogin()
    }

    @Override
    void afterEach(ExtensionContext context) throws Exception {
        Selenide.closeWebDriver()
    }
}
