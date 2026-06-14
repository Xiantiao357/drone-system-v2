package com.md.basePlatform.service.impl;

import com.md.basePlatform.domain.DroneAttributes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RuleBasedDroneAttributeGeneratorTest {

    @Test
    void should_generate_m300_attributes_when_model_matches() {
        RuleBasedDroneAttributeGenerator generator = new RuleBasedDroneAttributeGenerator();

        DroneAttributes attributes = generator.generate("DJI-M300", "DJI");

        assertEquals(55, attributes.getMaxFlightTime());
        assertEquals(15000, attributes.getMaxRange());
    }

    @Test
    void should_generate_default_attributes_when_model_unknown() {
        RuleBasedDroneAttributeGenerator generator = new RuleBasedDroneAttributeGenerator();

        DroneAttributes attributes = generator.generate("Unknown-X", "Acme");

        assertNotNull(attributes.getMaxFlightTime());
        assertNotNull(attributes.getMaxRange());
    }
}
