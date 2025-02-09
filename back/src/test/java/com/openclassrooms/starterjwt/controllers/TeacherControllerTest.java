package com.openclassrooms.starterjwt.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("TeacherController integration tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TeacherControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("findById valid integrationTest")
    @Test
    public void findById_shouldReturn200() throws Exception {
        this.mockMvc.perform(get("/api/teacher/1").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }

    @DisplayName("findById inValid integrationTest (teacher not exist)")
    @Test
    public void findById_shouldReturn404() throws Exception {
        this.mockMvc.perform(get("/api/teacher/999").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("findById inValid integrationTest without jwt")
    @Test
    public void findById_shouldReturn401() throws Exception {
        this.mockMvc.perform(get("/api/teacher/999"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("findById inValid integrationTest with invalidPath")
    @Test
    public void findById_shouldReturn400() throws Exception {
        this.mockMvc.perform(get("/api/teacher/blabla").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("findAll Valid integrationTest")
    @Test
    public void findAll_shouldReturn200() throws Exception {
        this.mockMvc.perform(get("/api/teacher/").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }
}
