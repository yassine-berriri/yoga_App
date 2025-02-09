package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UserMapper unit tests")
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapperUnderTest;

    User user;
    UserDto userDto;

    @BeforeEach
    public void setup() {

        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        userDto = new UserDto(1L, "yassine@gmail.com", "yassine", "yassine", true, "password", currentLocalDateTime, currentLocalDateTime);
        user = User.builder()
                .id(1L)
                .email("yassine@gmail.com")
                .admin(true)
                .firstName("yassine")
                .lastName("yassine")
                .createdAt(currentLocalDateTime)
                .updatedAt(currentLocalDateTime)
                .password("password")
                .build();
    }



    @Test
    @Tag("shouldHandleNullAdminInDto")
    public void shouldHandleNullAdminInDto() {
        userDto.setAdmin(false); // Simulation d'une valeur par défaut à `false`.

        User result = userMapperUnderTest.toEntity(userDto);

        assertFalse(result.isAdmin(), "Admin should be false when it is not explicitly set in DTO.");
    }

    @Test
    @Tag("shouldHandleEmptyListDtoToEntity")
    public void shouldHandleEmptyListDtoToEntity() {
        List<User> result = userMapperUnderTest.toEntity(Collections.emptyList());

        assertNotNull(result, "Result should not be null.");
        assertTrue(result.isEmpty(), "List should be empty when input list is empty.");
    }

    @Test
    @Tag("shouldHandleEmptyListEntityToDto")
    public void shouldHandleEmptyListEntityToDto() {
        List<UserDto> result = userMapperUnderTest.toDto(Collections.emptyList());

        assertNotNull(result, "Result should not be null.");
        assertTrue(result.isEmpty(), "List should be empty when input list is empty.");
    }

    @Test
    @Tag("shouldHandleNullCreatedAtAndUpdatedAtInUser")
    public void shouldHandleNullCreatedAtAndUpdatedAtInUser() {
        user.setCreatedAt(null);
        user.setUpdatedAt(null);

        UserDto result = userMapperUnderTest.toDto(user);

        assertNull(result.getCreatedAt(), "createdAt should be null when it is null in User.");
        assertNull(result.getUpdatedAt(), "updatedAt should be null when it is null in User.");
    }




}
