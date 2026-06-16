/**
 * 文件说明：后端测试类，用来验证 AI 自动生成无人机属性功能是否正常。
 */
package com.example.drone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.example.drone.domain.Drone;

class AiDroneProfileGeneratorTest {

    private final AiDroneProfileGenerator generator = new AiDroneProfileGenerator();

    @Test
    void generateShouldBeStableForSameName() {
        Drone first = generator.generate("巡检一号");
        Drone second = generator.generate("巡检一号");

        assertEquals(first.getSerialNumber(), second.getSerialNumber());
        assertEquals(first.getModel(), second.getModel());
        assertNotNull(first.getAiProfile());
    }
}
