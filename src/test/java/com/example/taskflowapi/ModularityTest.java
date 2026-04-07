package com.example.taskflowapi;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModularityTest {

    @Test
    void modulesShouldBeRespectEncapsulationLimits() {
        var modules = ApplicationModules.of(TaskflowApiApplication.class);
        modules.verify();
    }
}
