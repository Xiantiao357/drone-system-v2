package com.md.basePlatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.md.basePlatform.common.PageResult;
import com.md.basePlatform.domain.DroneCreateRequest;
import com.md.basePlatform.domain.DroneDTO;
import com.md.basePlatform.exception.BusinessException;
import com.md.basePlatform.exception.GlobalExceptionHandler;
import com.md.basePlatform.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DroneControllerTest {

    @Mock
    private DroneService droneService;

    @InjectMocks
    private DroneController droneController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(droneController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void should_return_drone_list_when_get_drones() throws Exception {
        DroneDTO dto = buildDto(1L);
        PageResult<DroneDTO> page = new PageResult<>(1, 1, 10, Collections.singletonList(dto));
        when(droneService.list(any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/drones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.items[0].model").value("DJI-M300"));
    }

    @Test
    void should_return_created_when_post_valid_drone() throws Exception {
        DroneCreateRequest request = new DroneCreateRequest();
        request.setModel("DJI-M300");
        request.setManufacturer("DJI");
        when(droneService.create(any())).thenReturn(buildDto(1L));

        mockMvc.perform(post("/api/v1/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("创建成功"));
    }

    @Test
    void should_return_bad_request_when_post_invalid_drone() throws Exception {
        DroneCreateRequest request = new DroneCreateRequest();

        mockMvc.perform(post("/api/v1/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void should_return_not_found_when_drone_missing() throws Exception {
        when(droneService.getById(99L)).thenThrow(new BusinessException(404, "无人机不存在"));

        mockMvc.perform(get("/api/v1/drones/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void should_return_success_when_delete_drone() throws Exception {
        mockMvc.perform(delete("/api/v1/drones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("删除成功"));
    }

    private DroneDTO buildDto(Long id) {
        DroneDTO dto = new DroneDTO();
        dto.setId(id);
        dto.setModel("DJI-M300");
        dto.setManufacturer("DJI");
        dto.setMaxFlightTime(55);
        dto.setMaxRange(15000);
        dto.setWeight(6.3);
        dto.setPayloadCapacity(2.7);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        return dto;
    }
}
