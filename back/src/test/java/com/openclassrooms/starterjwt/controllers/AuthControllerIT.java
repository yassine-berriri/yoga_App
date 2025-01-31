package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("AuthController integration tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerIT {

    @Autowired
    MockMvc mockMvc;

    SignupRequest signupRequest;
    LoginRequest loginRequest;

    @BeforeEach
    void setup(){
        signupRequest = new SignupRequest();
        loginRequest = new LoginRequest();
    }

    @DisplayName("authenticate valid integration test")
    @Test
    public void authenticate_shouldReturn200() throws Exception {
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");

        ObjectMapper objectMapper = new ObjectMapper();
        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);

        this.mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk());
    }

    @DisplayName("authenticate inValid integration test")
    @Test
    public void authenticate_shouldReturn401_withInvalidLogin() throws Exception {
        loginRequest.setEmail("test0@gmail.com");
        loginRequest.setPassword("test");

        ObjectMapper objectMapper = new ObjectMapper();
        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);

        this.mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("register Valid integration test")
    @Test
    public void register_shouldReturn200() throws Exception {
        String uniqueEmail = "user" + UUID.randomUUID().toString() + "@gmail.com";

        signupRequest.setEmail(uniqueEmail);
        signupRequest.setFirstName("toto99");
        signupRequest.setLastName("toto");
        signupRequest.setPassword("test1234885!");

        ObjectMapper objectMapper = new ObjectMapper();
        String signupRequestJson = objectMapper.writeValueAsString(signupRequest);

        this.mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupRequestJson))
                .andExpect(status().isOk());
    }

    @DisplayName("register inValid integration test")
    @Test
    public void register_shouldReturn401_whenUserExist() throws Exception {
        signupRequest.setEmail("test0@gmail.com");
        signupRequest.setFirstName("Admin");
        signupRequest.setLastName("Admin");
        signupRequest.setPassword("test12345");
        ObjectMapper objectMapper = new ObjectMapper();
        String signupRequestJson = objectMapper.writeValueAsString(signupRequest);

        this.mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupRequestJson))
                .andExpect(status().isBadRequest());
    }


}
