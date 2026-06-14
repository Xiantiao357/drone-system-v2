package com.md.basePlatform.service.impl;

import com.md.basePlatform.common.PageResult;
import com.md.basePlatform.domain.Drone;
import com.md.basePlatform.domain.DroneAttributes;
import com.md.basePlatform.domain.DroneCreateRequest;
import com.md.basePlatform.domain.DroneDTO;
import com.md.basePlatform.domain.DroneQueryRequest;
import com.md.basePlatform.domain.DroneUpdateRequest;
import com.md.basePlatform.exception.BusinessException;
import com.md.basePlatform.repository.DroneMapper;
import com.md.basePlatform.service.DroneAttributeGenerator;
import com.md.basePlatform.service.DroneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 无人机业务服务实现.
 */
@Service
public class DroneServiceImpl implements DroneService {

    private final DroneMapper droneMapper;
    private final DroneAttributeGenerator attributeGenerator;

    /**
     * 构造服务.
     *
     * @param droneMapper         Mapper
     * @param attributeGenerator  属性生成器
     */
    public DroneServiceImpl(DroneMapper droneMapper, DroneAttributeGenerator attributeGenerator) {
        this.droneMapper = droneMapper;
        this.attributeGenerator = attributeGenerator;
    }

    @Override
    @Transactional
    public DroneDTO create(DroneCreateRequest request) {
        DroneAttributes attributes = attributeGenerator.generate(request.getModel(), request.getManufacturer());
        LocalDateTime now = LocalDateTime.now();

        Drone drone = new Drone();
        drone.setModel(request.getModel());
        drone.setManufacturer(request.getManufacturer());
        drone.setMaxFlightTime(attributes.getMaxFlightTime());
        drone.setMaxRange(attributes.getMaxRange());
        drone.setWeight(attributes.getWeight());
        drone.setPayloadCapacity(attributes.getPayloadCapacity());
        drone.setCreatedAt(now);
        drone.setUpdatedAt(now);

        droneMapper.insert(drone);
        return DroneDTO.from(drone);
    }

    @Override
    public DroneDTO getById(Long id) {
        Drone drone = findExistingDrone(id);
        return DroneDTO.from(drone);
    }

    @Override
    public PageResult<DroneDTO> list(DroneQueryRequest query) {
        query.normalize();
        long total = droneMapper.countByKeyword(query.getKeyword());
        List<DroneDTO> items = droneMapper.selectPage(query.getOffset(), query.getSize(), query.getKeyword())
                .stream()
                .map(DroneDTO::from)
                .collect(Collectors.toList());
        return new PageResult<>(total, query.getPage(), query.getSize(), items);
    }

    @Override
    public List<DroneDTO> listAll(String keyword) {
        return droneMapper.selectAll(keyword)
                .stream()
                .map(DroneDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DroneDTO update(Long id, DroneUpdateRequest request) {
        if (!request.hasUpdateField()) {
            throw new BusinessException(400, "至少提供一个待更新字段");
        }

        Drone drone = findExistingDrone(id);
        if (request.getModel() != null) {
            drone.setModel(request.getModel());
        }
        if (request.getManufacturer() != null) {
            drone.setManufacturer(request.getManufacturer());
        }
        if (request.getMaxFlightTime() != null) {
            drone.setMaxFlightTime(request.getMaxFlightTime());
        }
        if (request.getMaxRange() != null) {
            drone.setMaxRange(request.getMaxRange());
        }
        if (request.getWeight() != null) {
            drone.setWeight(request.getWeight());
        }
        if (request.getPayloadCapacity() != null) {
            drone.setPayloadCapacity(request.getPayloadCapacity());
        }
        drone.setUpdatedAt(LocalDateTime.now());
        droneMapper.updateById(drone);
        return DroneDTO.from(drone);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findExistingDrone(id);
        droneMapper.deleteById(id);
    }

    private Drone findExistingDrone(Long id) {
        Drone drone = droneMapper.selectById(id);
        if (drone == null) {
            throw new BusinessException(404, "无人机不存在");
        }
        return drone;
    }
}
