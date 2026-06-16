/**
 * 文件说明：无人机数据操作接口，定义查询、保存、更新、删除等数据库操作。
 */
package com.example.drone.dao;

import java.util.List;

import com.example.drone.domain.Drone;
import com.example.drone.dto.DroneQuery;

public interface DroneDao {

    /**
     * 按条件查询无人机列表。
     */
    List<Drone> findAll(DroneQuery query);

    /**
     * 根据 id 查询单条无人机记录。
     */
    Drone findById(Long id);

    /**
     * 保存新无人机。
     */
    Drone save(Drone drone);

    /**
     * 更新无人机，成功返回 true。
     */
    boolean update(Drone drone);

    /**
     * 根据 id 删除无人机，成功返回 true。
     */
    boolean deleteById(Long id);

    /**
     * 判断序列号是否已经存在。
     * excludeId 用在修改场景，表示排除当前这条数据。
     */
    boolean existsBySerialNumber(String serialNumber, Long excludeId);
}
