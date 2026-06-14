package com.md.basePlatform.domain;

import java.time.LocalDateTime;

/**
 * 无人机响应 DTO.
 */
public class DroneDTO {

    private Long id;
    private String model;
    private String manufacturer;
    private Integer maxFlightTime;
    private Integer maxRange;
    private Double weight;
    private Double payloadCapacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 从实体转换为 DTO.
     *
     * @param drone 实体
     * @return DTO
     */
    public static DroneDTO from(Drone drone) {
        DroneDTO dto = new DroneDTO();
        dto.setId(drone.getId());
        dto.setModel(drone.getModel());
        dto.setManufacturer(drone.getManufacturer());
        dto.setMaxFlightTime(drone.getMaxFlightTime());
        dto.setMaxRange(drone.getMaxRange());
        dto.setWeight(drone.getWeight());
        dto.setPayloadCapacity(drone.getPayloadCapacity());
        dto.setCreatedAt(drone.getCreatedAt());
        dto.setUpdatedAt(drone.getUpdatedAt());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
