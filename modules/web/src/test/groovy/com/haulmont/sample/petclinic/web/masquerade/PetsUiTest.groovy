package com.haulmont.sample.petclinic.web.masquerade

import com.haulmont.sample.petclinic.web.masquerade.extension.DefaultLoginExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(DefaultLoginExtension)
class PetsUiTest {

    @Test
    @DisplayName('Create a pet')
    void createPet() {
        Assertions.assertEquals(1 + 1, 2)
    }
}
