package com.haulmont.sample.petclinic.web.masquerade.helper

import com.haulmont.masquerade.sys.TagNames
import org.openqa.selenium.Keys

import static com.codeborne.selenide.Selenide.$
import static com.haulmont.masquerade.Selectors.byChain
import static com.haulmont.masquerade.Selectors.byCubaId

class UiTestHelper {
    static void setTimeFieldValue(String dateFieldId, String value) {
        def timeFieldInput = $(byChain(byCubaId(dateFieldId),
                byCubaId('timepart'),
                TagNames.DIV,
                TagNames.INPUT))

        for (int i = 0; i < 4; i++) {
            timeFieldInput.sendKeys(Keys.BACK_SPACE)
        }

        timeFieldInput.sendKeys(value)
    }
}
