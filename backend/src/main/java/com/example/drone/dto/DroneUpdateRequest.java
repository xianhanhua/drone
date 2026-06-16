/**
 * 文件说明：修改无人机请求对象，接收前端提交的完整无人机信息。
 */
package com.example.drone.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DroneUpdateRequest {

    @NotBlank(message = "无人机名称不能为空")
    @Size(max = 100, message = "无人机名称不能超过100个字符")
    // 修改后的无人机名称。
    private String name;

    @NotBlank(message = "型号不能为空")
    // 修改后的型号。
    private String model;

    @NotBlank(message = "制造商不能为空")
    // 修改后的厂商。
    private String manufacturer;

    @NotBlank(message = "序列号不能为空")
    // 修改后的序列号，服务层会检查是否重复。
    private String serialNumber;

    @NotBlank(message = "类型不能为空")
    // 修改后的无人机类型。
    private String droneType;

    @Min(value = 1, message = "最长飞行时间必须大于0")
    @Max(value = 240, message = "最长飞行时间不能超过240分钟")
    // 最大续航时间，单位分钟。
    private Integer maxFlightTimeMinutes;

    @Min(value = 1, message = "最大速度必须大于0")
    @Max(value = 500, message = "最大速度不能超过500km/h")
    // 最大速度，单位 km/h。
    private Integer maxSpeedKmh;

    @Min(value = 1, message = "最大高度必须大于0")
    // 最大飞行高度，单位米。
    private Integer maxAltitudeMeters;

    @Min(value = 0, message = "载重不能为负数")
    // 最大载重，单位 kg。
    private BigDecimal payloadCapacityKg;

    @Min(value = 1, message = "电池容量必须大于0")
    // 电池容量，单位 mAh。
    private Integer batteryCapacityMah;

    @NotBlank(message = "状态不能为空")
    // 当前状态，例如待检、在役、维修、退役。
    private String status;

    // AI 生成的配置说明，也允许编辑时一起保存。
    private String aiProfile;

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
}
