/**
 * 文件说明：AI 属性生成器，根据无人机名称自动生成型号、厂商、参数和说明。
 */
package com.example.drone.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.example.drone.domain.Drone;

@Component
public class AiDroneProfileGenerator {

    // 这里准备几个模拟厂商名称，生成无人机时从中选一个。
    private static final String[] MANUFACTURERS = {
            "AeroVision", "SkyMatrix", "NorthRotor", "CloudForge", "HelioWorks"
    };

    // 这里准备几个模拟无人机类型，生成无人机时从中选一个。
    private static final String[] TYPES = {
            "巡检型", "测绘型", "物流型", "安防型", "农业型"
    };

    public Drone generate(String name) {
        // 名称为空时给一个默认名称，避免后面生成型号和序列号时报错。
        String safeName = name == null || name.trim().isEmpty() ? "AI-Drone" : name.trim();
        // 用名称算出一个固定数字。同一个名称会生成同一组属性，方便演示。
        int seed = positiveHash(safeName);

        // 根据 seed 生成一组看起来比较真实的无人机参数。
        Drone drone = new Drone();
        drone.setName(safeName);
        drone.setManufacturer(MANUFACTURERS[seed % MANUFACTURERS.length]);
        drone.setDroneType(TYPES[(seed / 7) % TYPES.length]);
        drone.setModel("AI-" + (seed % 900 + 100) + "-" + (char) ('A' + seed % 26));
        drone.setSerialNumber("DRN-" + shortDigest(safeName).toUpperCase());
        drone.setMaxFlightTimeMinutes(25 + seed % 66);
        drone.setMaxSpeedKmh(45 + seed % 96);
        drone.setMaxAltitudeMeters(800 + seed % 4201);
        drone.setPayloadCapacityKg(BigDecimal.valueOf(0.5 + (seed % 120) / 10.0).setScale(1, RoundingMode.HALF_UP));
        drone.setBatteryCapacityMah(4000 + seed % 16001);
        drone.setStatus("待检");
        drone.setAiProfile(buildProfile(drone));
        return drone;
    }

    private String buildProfile(Drone drone) {
        return "AI生成配置："
                + drone.getDroneType()
                + "，建议用于"
                + suggestedScenario(drone.getDroneType())
                + "；续航"
                + drone.getMaxFlightTimeMinutes()
                + "分钟，最大速度"
                + drone.getMaxSpeedKmh()
                + "km/h，载重"
                + drone.getPayloadCapacityKg()
                + "kg。";
    }

    private String suggestedScenario(String droneType) {
        // 根据无人机类型给出推荐使用场景。
        if ("巡检型".equals(droneType)) {
            return "电力、园区和管线巡查";
        }
        if ("测绘型".equals(droneType)) {
            return "地形测量和航拍建模";
        }
        if ("物流型".equals(droneType)) {
            return "轻载短途配送";
        }
        if ("安防型".equals(droneType)) {
            return "应急布控和安防巡逻";
        }
        return "农田监测和精准喷洒";
    }

    private int positiveHash(String value) {
        // hashCode 可能是负数，这里转成正数，方便后面做取模计算。
        return Math.abs(value.hashCode() == Integer.MIN_VALUE ? 0 : value.hashCode());
    }

    private String shortDigest(String value) {
        // 用 SHA-256 摘要生成短序列号，减少名称相近时序列号重复的概率。
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                builder.append(String.format("%02x", bytes[i]));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException ex) {
            return Integer.toHexString(positiveHash(value));
        }
    }
}
