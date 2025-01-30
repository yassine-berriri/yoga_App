package com.openclassrooms.starterjwt.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("UserController integration tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("findById valid integration test")
    @Test
    public void findById_shouldReturn200() throws Exception {
        this.mockMvc.perform(get("/api/user/1").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }

    @DisplayName("findById invalid integration test (without jwt)")
    @Test
    public void findById_shouldReturn401() throws Exception {
        this.mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isUnauthorized());
    }



    @DisplayName("findById invalid integration test (notFound user)")
    @Test
    public void findById_shouldReturn404() throws Exception {
        this.mockMvc.perform(get("/api/user/999").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("deleteUser invalid integration test (notFound user)")
    @Test
    public void deleteUser_shouldReturn404() throws Exception {
        this.mockMvc.perform(delete("/api/user/999").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("deleteUser invalid integration test (without jwt)")
    @Test
    public void deleteUser_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isUnauthorized());
    }


    @DisplayName("delete user integration test")
    @Test
    public void deleteUser_shouldReturn200() throws Exception {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("test0@gmail.com", "test12345!");
        this.mockMvc.perform(delete("/api/user/1").with(SecurityMockMvcRequestPostProcessors.authentication(authRequest)))
                .andExpect(status().is(200));
    }


}
