/**
 * 文件说明：MyBatis Mapper 接口，对应 DroneMapper.xml 中的 SQL。
 */
package com.example.drone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.drone.domain.Drone;
import com.example.drone.dto.DroneQuery;

public interface DroneMapper {

    /**
     * 查询无人机列表，对应 DroneMapper.xml 里的 selectList。
     */
    List<Drone> selectList(@Param("query") DroneQuery query);

    /**
     * 根据 id 查询无人机详情。
     */
    Drone selectById(@Param("id") Long id);

    /**
     * 插入一条无人机记录。
     */
    int insert(Drone drone);

    /**
     * 更新一条无人机记录。
     */
    int update(Drone drone);

    /**
     * 根据 id 删除一条无人机记录。
     */
    int deleteById(@Param("id") Long id);

    /**
     * 统计某个序列号出现次数，用来判断序列号是否重复。
     */
    int countBySerialNumber(@Param("serialNumber") String serialNumber, @Param("excludeId") Long excludeId);
}
