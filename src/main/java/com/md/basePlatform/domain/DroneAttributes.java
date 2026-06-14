package com.md.basePlatform.domain;

/**
 * AI 生成的无人机扩展属性.
 */
public class DroneAttributes {

    private Integer maxFlightTime;
    private Integer maxRange;
    private Double weight;
    private Double payloadCapacity;

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
