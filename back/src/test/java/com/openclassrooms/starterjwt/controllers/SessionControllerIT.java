package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Date;

@Tag("SessionController integration tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SessionControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private SessionMapper sessionMapper;

    String sessionJson;

    @BeforeEach
    void setup() throws JsonProcessingException {
        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("teacher")
                .lastName("teacher")
                .build();

        Session session = Session.builder()
                .name("Session updated")
                .description("description updated")
                .date(new Date())
                .teacher(teacher)
                .build();

        SessionDto sessionDto = sessionMapper.toDto(session);

        sessionJson = new ObjectMapper().writeValueAsString(sessionDto);
    }

    @DisplayName("findById valid integrationTest")
    @Test
    public void findById_shouldReturn200() throws Exception {
        this.mockMvc.perform(get("/api/session/5").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }

    @DisplayName("findById inValid integrationTest (session not exist)")
    @Test
    public void findById_shouldReturn404() throws Exception {
        this.mockMvc.perform(get("/api/session/999").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("findById inValid integrationTest without jwt")
    @Test
    public void findById_shouldReturn401() throws Exception {
        this.mockMvc.perform(get("/api/session/999"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("findById inValid integrationTest with invalidPath")
    @Test
    public void findById_shouldReturn400() throws Exception {
        this.mockMvc.perform(get("/api/session/blabla").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("findAll Valid integrationTest")
    @Test
    public void findAll_shouldReturn200() throws Exception {
        this.mockMvc.perform(get("/api/session").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }

    @DisplayName("create Valid integrationTest")
    @Test
    public void create_shouldReturn200() throws Exception {
        this.mockMvc.perform(post("/api/session").with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionJson))
                .andExpect(status().isOk());
    }

    @DisplayName("update Valid integrationTest")
    @Test
    public void update_shouldReturn200() throws Exception {
        this.mockMvc.perform(put("/api/session/1").with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionJson))
                .andExpect(status().isOk());
    }

    @DisplayName("update inValid integrationTest invalid path")
    @Test
    public void update_shouldReturn400() throws Exception {
        this.mockMvc.perform(put("/api/session/blabla").with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionJson))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("delete inValid integrationTest invalid path")
    @Test
    public void delete_shouldReturn400() throws Exception {
        this.mockMvc.perform(delete("/api/session/blabla").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("delete inValid integrationTest with not exist session")
    @Test
    public void delete_shouldReturn404() throws Exception {
        this.mockMvc.perform(delete("/api/session/99999").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());
    }

    @Disabled
    @DisplayName("delete Valid integrationTest")
    @Test
    public void delete_shouldReturn200() throws Exception {
        this.mockMvc.perform(delete("/api/session/2").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }

    @DisplayName("participate Valid integrationTest")
    @Test
    public void participateAndNotLongerParticipate_shouldReturn200() throws Exception {
        this.mockMvc.perform(post("/api/session/3/participate/2").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
        this.mockMvc.perform(delete("/api/session/3/participate/2").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }

    @DisplayName("noLongerParticipate Valid integrationTest")
    @Test
    public void noLongerParticipate_shouldReturn200() throws Exception {
        this.mockMvc.perform(post("/api/session/4/participate/2").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/api/session/4/participate/2").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }


    @DisplayName("participate inValid integrationTest (not exist session)")
    @Test
    public void participate_shouldReturn404() throws Exception {
        this.mockMvc.perform(post("/api/session/1/participate/2").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());
    }

}
