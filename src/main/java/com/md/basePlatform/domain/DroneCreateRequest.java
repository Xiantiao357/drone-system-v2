package com.md.basePlatform.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 创建无人机请求.
 */
public class DroneCreateRequest {

    @NotBlank(message = "型号不能为空")
    @Size(max = 100, message = "型号长度不能超过100")
    private String model;

    @NotBlank(message = "厂商不能为空")
    @Size(max = 100, message = "厂商长度不能超过100")
    private String manufacturer;

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
}
