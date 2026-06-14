package com.md.basePlatform.service.impl;

import com.md.basePlatform.domain.Drone;
import com.md.basePlatform.domain.DroneAttributes;
import com.md.basePlatform.domain.DroneCreateRequest;
import com.md.basePlatform.domain.DroneDTO;
import com.md.basePlatform.domain.DroneQueryRequest;
import com.md.basePlatform.domain.DroneUpdateRequest;
import com.md.basePlatform.exception.BusinessException;
import com.md.basePlatform.repository.DroneMapper;
import com.md.basePlatform.service.DroneAttributeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DroneServiceImplTest {

    @Mock
    private DroneMapper droneMapper;

    @Mock
    private DroneAttributeGenerator attributeGenerator;

    @InjectMocks
    private DroneServiceImpl droneService;

    @Test
    void should_create_drone_when_valid_request() {
        DroneCreateRequest request = new DroneCreateRequest();
        request.setModel("DJI-M300");
        request.setManufacturer("DJI");

        DroneAttributes attributes = new DroneAttributes();
        attributes.setMaxFlightTime(55);
        attributes.setMaxRange(15000);
        attributes.setWeight(6.3);
        attributes.setPayloadCapacity(2.7);
        when(attributeGenerator.generate("DJI-M300", "DJI")).thenReturn(attributes);
        when(droneMapper.insert(any(Drone.class))).thenAnswer(invocation -> {
            Drone drone = invocation.getArgument(0);
            drone.setId(1L);
            return 1;
        });

        DroneDTO result = droneService.create(request);

        assertEquals("DJI-M300", result.getModel());
        assertEquals(55, result.getMaxFlightTime());
        verify(droneMapper).insert(any(Drone.class));
    }

    @Test
    void should_throw_not_found_when_drone_missing() {
        when(droneMapper.selectById(99L)).thenReturn(null);

        BusinessException ex = assertThrows(BusinessException.class, () -> droneService.getById(99L));

        assertEquals(404, ex.getCode());
    }

    @Test
    void should_return_page_result_when_list_drones() {
        Drone drone = buildDrone(1L);
        when(droneMapper.countByKeyword(null)).thenReturn(1L);
        when(droneMapper.selectPage(0, 10, null)).thenReturn(Collections.singletonList(drone));

        DroneQueryRequest query = new DroneQueryRequest();
        var page = droneService.list(query);

        assertEquals(1, page.getTotal());
        assertEquals(1, page.getItems().size());
    }

    @Test
    void should_update_drone_when_fields_provided() {
        Drone existing = buildDrone(1L);
        when(droneMapper.selectById(1L)).thenReturn(existing);

        DroneUpdateRequest request = new DroneUpdateRequest();
        request.setModel("DJI-M300-RTK");

        DroneDTO updated = droneService.update(1L, request);

        assertEquals("DJI-M300-RTK", updated.getModel());
        verify(droneMapper).updateById(existing);
    }

    @Test
    void should_throw_bad_request_when_update_has_no_fields() {
        DroneUpdateRequest request = new DroneUpdateRequest();

        BusinessException ex = assertThrows(BusinessException.class,
                () -> droneService.update(1L, request));

        assertEquals(400, ex.getCode());
    }

    @Test
    void should_delete_drone_when_exists() {
        when(droneMapper.selectById(1L)).thenReturn(buildDrone(1L));

        droneService.delete(1L);

        verify(droneMapper).deleteById(1L);
    }

    private Drone buildDrone(Long id) {
        Drone drone = new Drone();
        drone.setId(id);
        drone.setModel("DJI-M300");
        drone.setManufacturer("DJI");
        drone.setMaxFlightTime(55);
        drone.setMaxRange(15000);
        drone.setWeight(6.3);
        drone.setPayloadCapacity(2.7);
        drone.setCreatedAt(LocalDateTime.now());
        drone.setUpdatedAt(LocalDateTime.now());
        return drone;
    }
}
