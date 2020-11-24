package org.example.meteorites.microscope;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.example.meteorites.microscope");

        noClasses()
            .that()
            .resideInAnyPackage("org.example.meteorites.microscope.service..")
            .or()
            .resideInAnyPackage("org.example.meteorites.microscope.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..org.example.meteorites.microscope.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
