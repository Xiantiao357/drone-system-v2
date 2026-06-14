package com.md.basePlatform.domain;

import javax.validation.constraints.Size;

/**
 * 更新无人机请求.
 */
public class DroneUpdateRequest {

    @Size(max = 100, message = "型号长度不能超过100")
    private String model;

    @Size(max = 100, message = "厂商长度不能超过100")
    private String manufacturer;

    private Integer maxFlightTime;
    private Integer maxRange;
    private Double weight;
    private Double payloadCapacity;

    /**
     * 判断是否至少有一个字段待更新.
     *
     * @return 是否有更新字段
     */
    public boolean hasUpdateField() {
        return model != null || manufacturer != null || maxFlightTime != null
                || maxRange != null || weight != null || payloadCapacity != null;
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

    public Integer getMaxFlightTime() {
        return maxFlightTime;
    }

    public void setMaxFlightTime(Integer maxFlightTime) {
        this.maxFlightTime = maxFlightTime;
    }

    public Integer getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(Integer maxRange) {
        this.maxRange = maxRange;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPayloadCapacity() {
        return payloadCapacity;
    }

    public void setPayloadCapacity(Double payloadCapacity) {
        this.payloadCapacity = payloadCapacity;
    }
}
