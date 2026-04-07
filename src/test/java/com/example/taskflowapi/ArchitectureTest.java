package com.example.taskflowapi;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "com.example.taskflowapi",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ArchitectureTest {
    @ArchTest
    static final ArchRule architectureInLayersMustBeRespected = layeredArchitecture()
            .consideringAllDependencies()
            .withOptionalLayers(true)
            // Clean architecture layers
            .layer("Domain").definedBy("..domain..")
            .layer("Application").definedBy("..application..")
            .layer("Infrastructure").definedBy("..infrastructure..")
            .layer("Presentation").definedBy("..presentation..")
            .layer("Shared").definedBy("..shared..")
            // Define access layers rules
            .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
            .whereLayer("Application").mayOnlyBeAccessedByLayers("Presentation", "Infrastructure", "Shared")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure", "Presentation", "Shared");

    @ArchTest
    static final ArchRule domainMustBePure = classes()
            .that().resideInAnyPackage("..domain..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage(
                    "java..", // Permitir clases del JDK estándar
                    "..domain..",              // Permitir clases dentro del propio dominio (ej. Order depende de OrderItem)
                    "lombok.."                 // Permitir anotaciones de Lombok
            )
            .allowEmptyShould(true)
            .because("El dominio es el núcleo de negocio. Solo se permiten clases nativas de Java y Lombok para reducir boilerplate.");
}
