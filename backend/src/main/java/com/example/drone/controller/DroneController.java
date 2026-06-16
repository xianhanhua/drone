/**
 * 文件说明：无人机接口控制器，负责查询、新增、修改、删除和 AI 预览接口。
 */
package com.example.drone.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.drone.domain.Drone;
import com.example.drone.dto.ApiResponse;
import com.example.drone.dto.DroneCreateRequest;
import com.example.drone.dto.DroneQuery;
import com.example.drone.dto.DroneUpdateRequest;
import com.example.drone.service.DroneService;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    /**
     * 控制器本身不直接写业务规则，只调用服务层。
     * 这样接口入口和业务处理分开，代码更清楚。
     */
    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public ApiResponse<List<Drone>> list(@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String status) {
        // 把前端传来的查询条件封装成 DroneQuery，再交给服务层查询。
        DroneQuery query = new DroneQuery();
        query.setKeyword(keyword);
        query.setStatus(status);
        return ApiResponse.ok(droneService.findAll(query));
    }

    @GetMapping("/{id}")
    public ApiResponse<Drone> detail(@PathVariable Long id) {
        // 根据地址里的 id 查询某一架无人机详情。
        return ApiResponse.ok(droneService.findById(id));
    }

    @PostMapping
    public ApiResponse<Drone> create(@Valid @RequestBody DroneCreateRequest request) {
        // 新增时只需要前端传名称和状态，其他属性由服务层自动生成。
        return ApiResponse.ok("创建成功", droneService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Drone> update(@PathVariable Long id, @Valid @RequestBody DroneUpdateRequest request) {
        // 修改时 id 来自路径，具体修改内容来自请求体。
        return ApiResponse.ok("更新成功", droneService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        // 删除前服务层会先检查这条数据是否存在。
        droneService.delete(id);
        return ApiResponse.ok("删除成功", null);
    }

    @GetMapping("/ai-preview")
    public ApiResponse<Drone> aiPreview(@RequestParam String name) {
        // 给前端预览 AI 自动生成的属性，不直接保存数据库。
        return ApiResponse.ok(droneService.generateAiProfile(name));
    }
}
