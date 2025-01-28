package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@Tag("UserService Unit tests")
@SpringBootTest
public class UserServiceTest {

    private UserService userServiceUnderTest;

    @Mock
    UserRepository userRepository;

    User user;

    @BeforeEach
    public void setup(){
        userServiceUnderTest = new UserService(userRepository);

        user = user.builder()
                .id(1L)
                .email("yassine@gmail.com")
                .admin(true)
                .firstName("yassine")
                .lastName("yassine")
                .password("password")
                .build();
    }

    @DisplayName("findUserById valid test")
    @Test
    public void findById_shouldFindUser_givenId() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isEqualTo(user);
    }

    @DisplayName("findUserById inValid test")
    @Test
    public void findById_shouldNotFindUser_givenInvalidId() {
        // Arrange
        Long id = 0L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        User result = userServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isEqualTo(null);
    }

    @DisplayName("deleteById")
    @Test
    public void should_shouldDeleteUser_givenId() {
        // Arrange
        Long id = 1L;
        doNothing().when(userRepository).deleteById(id);

        // Act
        userServiceUnderTest.delete(id);

        // Assert
        verify(userRepository, times(1)).deleteById(id);
    }




}
