/**
 * 文件说明：新增无人机请求对象，接收前端提交的名称和状态。
 */
package com.example.drone.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DroneCreateRequest {

    @NotBlank(message = "无人机名称不能为空")
    @Size(max = 100, message = "无人机名称不能超过100个字符")
    // 新增无人机时必须填写名称；型号、厂商、续航等由 AI 生成器补充。
    private String name;

    // 初始状态，前端可以传待检、在役等值。
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
