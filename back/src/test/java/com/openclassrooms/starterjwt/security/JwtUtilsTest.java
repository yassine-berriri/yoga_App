package com.openclassrooms.starterjwt.security;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

@Tag("JwtUtils unit tests")
@SpringBootTest
public class JwtUtilsTest {

    @Autowired
    JwtUtils jwtUtilsUnderTest;

    @Autowired
    private AuthenticationManager authManager;

    @DisplayName("getUserNameFromJwtToken valid test")
    @Test
    public void getUserNameFromJwtToken(){
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("yoga@studio.com","test!1234");
        Authentication authentication = authManager.authenticate(authRequest);
        String token = jwtUtilsUnderTest.generateJwtToken(authentication);

        assertEquals(jwtUtilsUnderTest.getUserNameFromJwtToken(token), "yoga@studio.com");
    }

    @DisplayName("validateJwtToken valid test")
    @Test
    public void validateJwtToken_shouldReturnTrue_whenValidToken() {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("yoga@studio.com","test!1234");
        Authentication authentication = authManager.authenticate(authRequest);

        assertTrue(jwtUtilsUnderTest.validateJwtToken(jwtUtilsUnderTest.generateJwtToken(authentication)));
    }

    @DisplayName("validateJwtToken valid test")
    @Test
    public void validateJwtToken_shouldReturnFalse_whenValidToken() {
        assertFalse(jwtUtilsUnderTest.validateJwtToken("invalid token"));
    }

}
