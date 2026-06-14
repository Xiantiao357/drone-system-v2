package com.md.basePlatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.basePlatform.domain.DroneCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DroneIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_complete_crud_flow_when_valid_requests() throws Exception {
        DroneCreateRequest createRequest = new DroneCreateRequest();
        createRequest.setModel("DJI-M300");
        createRequest.setManufacturer("DJI");

        String createResponse = mockMvc.perform(post("/api/v1/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.model").value("DJI-M300"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(createResponse).path("data").path("id").asLong();

        mockMvc.perform(get("/api/v1/drones/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.maxFlightTime").value(55));

        mockMvc.perform(put("/api/v1/drones/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"model\":\"DJI-M300-RTK\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.model").value("DJI-M300-RTK"));

        mockMvc.perform(delete("/api/v1/drones/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("删除成功"));

        mockMvc.perform(get("/api/v1/drones/" + id))
                .andExpect(status().isNotFound());
    }
}
