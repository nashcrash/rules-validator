package it.sitissimo.validation;

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
            .importPackages("it.sitissimo.validation");

        noClasses()
            .that()
            .resideInAnyPackage("it.sitissimo.validation.service..")
            .or()
            .resideInAnyPackage("it.sitissimo.validation.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..it.sitissimo.validation.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
