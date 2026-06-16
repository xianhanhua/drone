/**
 * 文件说明：无人机实体类，对应数据库 drones 表的一条记录。
 */
package com.example.drone.domain;

import java.math.BigDecimal;

public class Drone {

    // 数据库主键，每一架无人机都有唯一 id。
    private Long id;

    // 无人机名称，页面表格第一列展示。
    private String name;

    // 型号，例如 Matrice 30T、CW-15 等。
    private String model;

    // 厂商名称。
    private String manufacturer;

    // 序列号，用来区分同型号的不同设备，要求不能重复。
    private String serialNumber;

    // 无人机类型，例如巡检型、测绘型、物流型等。
    private String droneType;

    // 最大续航时间，单位是分钟。
    private Integer maxFlightTimeMinutes;

    // 最大速度，单位是 km/h。
    private Integer maxSpeedKmh;

    // 最大飞行高度，单位是米。
    private Integer maxAltitudeMeters;

    // 最大载重，单位是 kg。用 BigDecimal 是为了小数更精确。
    private BigDecimal payloadCapacityKg;

    // 电池容量，单位是 mAh。
    private Integer batteryCapacityMah;

    // 当前状态，例如待检、在役、维修、退役。
    private String status;

    // AI 自动生成的说明文字，用来描述推荐用途和主要参数。
    private String aiProfile;

    // 创建时间，保存成字符串是为了兼容 SQLite 时间字段读取。
    private String createdAt;

    // 最后更新时间。
    private String updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDroneType() {
        return droneType;
    }

    public void setDroneType(String droneType) {
        this.droneType = droneType;
    }

    public Integer getMaxFlightTimeMinutes() {
        return maxFlightTimeMinutes;
    }

    public void setMaxFlightTimeMinutes(Integer maxFlightTimeMinutes) {
        this.maxFlightTimeMinutes = maxFlightTimeMinutes;
    }

    public Integer getMaxSpeedKmh() {
        return maxSpeedKmh;
    }

    public void setMaxSpeedKmh(Integer maxSpeedKmh) {
        this.maxSpeedKmh = maxSpeedKmh;
    }

    public Integer getMaxAltitudeMeters() {
        return maxAltitudeMeters;
    }

    public void setMaxAltitudeMeters(Integer maxAltitudeMeters) {
        this.maxAltitudeMeters = maxAltitudeMeters;
    }

    public BigDecimal getPayloadCapacityKg() {
        return payloadCapacityKg;
    }

    public void setPayloadCapacityKg(BigDecimal payloadCapacityKg) {
        this.payloadCapacityKg = payloadCapacityKg;
    }

    public Integer getBatteryCapacityMah() {
        return batteryCapacityMah;
    }

    public void setBatteryCapacityMah(Integer batteryCapacityMah) {
        this.batteryCapacityMah = batteryCapacityMah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAiProfile() {
        return aiProfile;
    }

    public void setAiProfile(String aiProfile) {
        this.aiProfile = aiProfile;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
