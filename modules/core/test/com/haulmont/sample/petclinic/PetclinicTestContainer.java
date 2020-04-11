package com.haulmont.sample.petclinic;

import com.haulmont.bali.util.Dom4j;
import com.haulmont.cuba.testsupport.TestContainer;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class PetclinicTestContainer extends TestContainer {

    public PetclinicTestContainer() {
        super();
        appComponents = new ArrayList<>(Arrays.asList(
                "com.haulmont.cuba"
        ));
        appPropertiesFiles = Arrays.asList(
                "com/haulmont/sample/petclinic/app.properties",
                "com/haulmont/sample/petclinic/integrationtest-app.properties",
                "com/haulmont/sample/petclinic/test-app.properties"
        );

        autoConfigureDataSource();
    }

    public static class Common extends PetclinicTestContainer {

        public static final PetclinicTestContainer.Common INSTANCE = new PetclinicTestContainer.Common();

        private static volatile boolean initialized;

        private Common() {
        }


        @Override
        public void beforeAll(ExtensionContext extensionContext) throws Exception {
            if (!initialized) {
                super.beforeAll(extensionContext);
                initialized = true;
            }
            setupContext();
        }

        @SuppressWarnings("RedundantThrows")
        @Override
        public void afterAll(ExtensionContext extensionContext) throws Exception {
            cleanupContext();
            // never stops - do not call super
        }
    }
}