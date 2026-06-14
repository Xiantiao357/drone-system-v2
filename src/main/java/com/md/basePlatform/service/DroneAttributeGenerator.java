package com.md.basePlatform.service;

import com.md.basePlatform.domain.DroneAttributes;

/**
 * 无人机 AI 属性生成器.
 */
public interface DroneAttributeGenerator {

    /**
     * 根据型号与厂商生成扩展属性.
     *
     * @param model        型号
     * @param manufacturer 厂商
     * @return 生成的属性
     */
    DroneAttributes generate(String model, String manufacturer);
}
