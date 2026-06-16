/**
 * 文件说明：无人机业务实现类，处理核心业务逻辑并调用数据层。
 */
package com.example.drone.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.drone.dao.DroneDao;
import com.example.drone.domain.Drone;
import com.example.drone.dto.DroneCreateRequest;
import com.example.drone.dto.DroneQuery;
import com.example.drone.dto.DroneUpdateRequest;
import com.example.drone.exception.BusinessException;
import com.example.drone.service.AiDroneProfileGenerator;
import com.example.drone.service.DroneService;

@Service
public class DroneServiceImpl implements DroneService {

    /**
     * 统一时间格式，保存到 SQLite 时更稳定，也方便页面显示。
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 数据访问对象，负责真正查询和修改数据库。
     */
    private final DroneDao droneDao;

    /**
     * AI 属性生成器，新增无人机和预览属性时都会用到。
     */
    private final AiDroneProfileGenerator aiDroneProfileGenerator;

    public DroneServiceImpl(DroneDao droneDao, AiDroneProfileGenerator aiDroneProfileGenerator) {
        this.droneDao = droneDao;
        this.aiDroneProfileGenerator = aiDroneProfileGenerator;
    }

    @Override
    public List<Drone> findAll(DroneQuery query) {
        // 查询逻辑比较简单，直接交给 DAO。
        return droneDao.findAll(query);
    }

    @Override
    public Drone findById(Long id) {
        // 先查数据库，查不到就抛业务异常，让前端看到明确提示。
        Drone drone = droneDao.findById(id);
        if (drone == null) {
            throw new BusinessException("Drone not found");
        }
        return drone;
    }

    @Override
    @Transactional
    public Drone create(DroneCreateRequest request) {
        // 新增时先根据名称生成完整无人机资料。
        Drone drone = aiDroneProfileGenerator.generate(request.getName());
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            // 如果前端传了状态，就覆盖生成器的默认状态。
            drone.setStatus(request.getStatus().trim());
        }
        // 避免自动生成的序列号和数据库已有序列号重复。
        makeSerialNumberUnique(drone);
        String now = now();
        drone.setCreatedAt(now);
        drone.setUpdatedAt(now);
        return droneDao.save(drone);
    }

    @Override
    @Transactional
    public Drone update(Long id, DroneUpdateRequest request) {
        // 修改前先确认数据存在，避免更新不存在的记录。
        Drone existing = findById(id);
        existing.setName(request.getName().trim());
        existing.setModel(request.getModel().trim());
        existing.setManufacturer(request.getManufacturer().trim());
        existing.setSerialNumber(request.getSerialNumber().trim());
        existing.setDroneType(request.getDroneType().trim());
        existing.setMaxFlightTimeMinutes(request.getMaxFlightTimeMinutes());
        existing.setMaxSpeedKmh(request.getMaxSpeedKmh());
        existing.setMaxAltitudeMeters(request.getMaxAltitudeMeters());
        existing.setPayloadCapacityKg(request.getPayloadCapacityKg());
        existing.setBatteryCapacityMah(request.getBatteryCapacityMah());
        existing.setStatus(request.getStatus().trim());
        existing.setAiProfile(request.getAiProfile());
        existing.setUpdatedAt(now());

        // 序列号是唯一标识，修改时也要检查是否和其他无人机重复。
        ensureUniqueSerialNumber(existing);
        if (!droneDao.update(existing)) {
            throw new BusinessException("Failed to update drone");
        }
        return existing;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 删除前先查一次，查不到会抛出“数据不存在”的业务异常。
        findById(id);
        if (!droneDao.deleteById(id)) {
            throw new BusinessException("Failed to delete drone");
        }
    }

    @Override
    public Drone generateAiProfile(String name) {
        // 只生成数据给前端预览，不写入数据库。
        return aiDroneProfileGenerator.generate(name);
    }

    private void ensureUniqueSerialNumber(Drone drone) {
        // 修改时要排除自己这条记录，所以传入当前 id。
        if (droneDao.existsBySerialNumber(drone.getSerialNumber(), drone.getId())) {
            throw new BusinessException("Serial number already exists");
        }
    }

    private void makeSerialNumberUnique(Drone drone) {
        // 新增时如果序列号重复，就在后面追加 -1、-2 这样的后缀。
        String baseSerialNumber = drone.getSerialNumber();
        int suffix = 1;
        while (droneDao.existsBySerialNumber(drone.getSerialNumber(), null)) {
            drone.setSerialNumber(baseSerialNumber + "-" + suffix);
            suffix++;
        }
    }

    private String now() {
        // 集中生成当前时间，避免多个地方格式不一致。
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }
}
