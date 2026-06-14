package com.md.basePlatform.repository;

import com.md.basePlatform.domain.Drone;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/schema.sql")
@TestPropertySource(properties = "spring.datasource.url=jdbc:sqlite:mem:mapper-test;MODE=MySQL")
class DroneMapperTest {

    @Autowired
    private DroneMapper droneMapper;

    @Test
    void should_insert_and_select_drone_when_valid_data() {
        Drone drone = buildDrone("DJI-M300", "DJI");
        droneMapper.insert(drone);

        Drone found = droneMapper.selectById(drone.getId());

        assertNotNull(found);
        assertEquals("DJI-M300", found.getModel());
    }

    @Test
    void should_select_page_when_drones_exist() {
        droneMapper.insert(buildDrone("DJI-M300", "DJI"));
        droneMapper.insert(buildDrone("DJI-Mini", "DJI"));

        List<Drone> page = droneMapper.selectPage(0, 10, "DJI");
        long total = droneMapper.countByKeyword("DJI");

        assertEquals(2, total);
        assertEquals(2, page.size());
    }

    @Test
    void should_update_and_delete_drone_when_exists() {
        Drone drone = buildDrone("DJI-M300", "DJI");
        droneMapper.insert(drone);

        drone.setModel("DJI-M300-RTK");
        drone.setUpdatedAt(LocalDateTime.now());
        droneMapper.updateById(drone);

        assertEquals("DJI-M300-RTK", droneMapper.selectById(drone.getId()).getModel());

        droneMapper.deleteById(drone.getId());
        assertEquals(0, droneMapper.countByKeyword(null));
    }

    private Drone buildDrone(String model, String manufacturer) {
        LocalDateTime now = LocalDateTime.now();
        Drone drone = new Drone();
        drone.setModel(model);
        drone.setManufacturer(manufacturer);
        drone.setMaxFlightTime(55);
        drone.setMaxRange(15000);
        drone.setWeight(6.3);
        drone.setPayloadCapacity(2.7);
        drone.setCreatedAt(now);
        drone.setUpdatedAt(now);
        return drone;
    }
}
