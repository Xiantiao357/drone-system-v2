package com.md.basePlatform.service.impl;

import com.md.basePlatform.domain.DroneAttributes;
import com.md.basePlatform.service.DroneAttributeGenerator;
import org.springframework.stereotype.Service;

/**
 * 基于规则的无人机属性生成实现（AI 占位）.
 */
@Service
public class RuleBasedDroneAttributeGenerator implements DroneAttributeGenerator {

    @Override
    public DroneAttributes generate(String model, String manufacturer) {
        if (model == null || manufacturer == null) {
            throw new IllegalArgumentException("model and manufacturer must not be null");
        }

        DroneAttributes attributes = new DroneAttributes();
        String modelUpper = model.toUpperCase();

        if (modelUpper.contains("M300")) {
            fillM300Attributes(attributes);
        }
        else if (modelUpper.contains("MINI")) {
            fillMiniAttributes(attributes);
        }
        else {
            fillDefaultAttributes(attributes, model, manufacturer);
        }
        return attributes;
    }

    private void fillM300Attributes(DroneAttributes attributes) {
        attributes.setMaxFlightTime(55);
        attributes.setMaxRange(15000);
        attributes.setWeight(6.3);
        attributes.setPayloadCapacity(2.7);
    }

    private void fillMiniAttributes(DroneAttributes attributes) {
        attributes.setMaxFlightTime(31);
        attributes.setMaxRange(10000);
        attributes.setWeight(0.25);
        attributes.setPayloadCapacity(0.0);
    }

    private void fillDefaultAttributes(DroneAttributes attributes, String model, String manufacturer) {
        int seed = (model + manufacturer).hashCode() & 0x7FFFFFFF;
        attributes.setMaxFlightTime(20 + seed % 40);
        attributes.setMaxRange(5000 + seed % 15000);
        attributes.setWeight(1.0 + (seed % 100) / 10.0);
        attributes.setPayloadCapacity((seed % 50) / 10.0);
    }
}
