/**
 * 文件说明：无人机业务接口，定义查询、新增、修改、删除等业务方法。
 */
package com.example.drone.service;

import java.util.List;

import com.example.drone.domain.Drone;
import com.example.drone.dto.DroneCreateRequest;
import com.example.drone.dto.DroneQuery;
import com.example.drone.dto.DroneUpdateRequest;

public interface DroneService {

    /**
     * 按关键词和状态查询无人机列表。
     */
    List<Drone> findAll(DroneQuery query);

    /**
     * 根据 id 查询单架无人机。
     */
    Drone findById(Long id);

    /**
     * 新增无人机，属性由 AI 生成器补全。
     */
    Drone create(DroneCreateRequest request);

    /**
     * 修改指定 id 的无人机。
     */
    Drone update(Long id, DroneUpdateRequest request);

    /**
     * 删除指定 id 的无人机。
     */
    void delete(Long id);

    /**
     * 只生成预览数据，不保存数据库。
     */
    Drone generateAiProfile(String name);
}
