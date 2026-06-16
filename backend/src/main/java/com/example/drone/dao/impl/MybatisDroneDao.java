/**
 * 文件说明：DroneDao 的 MyBatis 实现类，调用 Mapper 执行具体 SQL。
 */
package com.example.drone.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.drone.dao.DroneDao;
import com.example.drone.domain.Drone;
import com.example.drone.dto.DroneQuery;
import com.example.drone.mapper.DroneMapper;

@Repository
public class MybatisDroneDao implements DroneDao {

    /**
     * MyBatis Mapper，真正执行 mapper XML 里的 SQL。
     */
    private final DroneMapper droneMapper;

    public MybatisDroneDao(DroneMapper droneMapper) {
        this.droneMapper = droneMapper;
    }

    @Override
    public List<Drone> findAll(DroneQuery query) {
        // 调用 DroneMapper.xml 里的 selectList SQL。
        return droneMapper.selectList(query);
    }

    @Override
    public Drone findById(Long id) {
        // 调用 DroneMapper.xml 里的 selectById SQL。
        return droneMapper.selectById(id);
    }

    @Override
    public Drone save(Drone drone) {
        // insert 后会把数据库生成的 id 回填到 drone 对象里。
        droneMapper.insert(drone);
        return drone;
    }

    @Override
    public boolean update(Drone drone) {
        // MyBatis update 返回影响行数，大于 0 说明更新成功。
        return droneMapper.update(drone) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        // 删除影响行数大于 0 说明确实删到了一条记录。
        return droneMapper.deleteById(id) > 0;
    }

    @Override
    public boolean existsBySerialNumber(String serialNumber, Long excludeId) {
        // 用 count 查询是否存在相同序列号。
        return droneMapper.countBySerialNumber(serialNumber, excludeId) > 0;
    }
}
