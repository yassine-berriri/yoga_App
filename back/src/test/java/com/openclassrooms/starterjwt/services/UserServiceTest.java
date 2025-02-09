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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@Tag("UserService Unit tests")
@SpringBootTest
public class UserServiceTest {

    private UserService userServiceUnderTest;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup(){
        userServiceUnderTest = new UserService(userRepository);

        user = User.builder()
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
    public void findById_shouldFindUser_givenValidId() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isEqualTo(user);
    }

    @DisplayName("findUserById invalid test")
    @Test
    public void findById_shouldReturnNull_givenInvalidId() {
        // Arrange
        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        User result = userServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isNull();
    }



    @DisplayName("findUserById with negative ID test")
    @Test
    public void findById_shouldReturnNull_givenNegativeId() {
        // Arrange
        Long id = -1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        User result = userServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isNull();
    }

    @DisplayName("deleteById valid test")
    @Test
    public void delete_shouldDeleteUser_givenValidId() {
        // Arrange
        Long id = 1L;
        doNothing().when(userRepository).deleteById(id);

        // Act
        userServiceUnderTest.delete(id);

        // Assert
        verify(userRepository, times(1)).deleteById(id);
    }

    @DisplayName("deleteById with invalid ID test")
    @Test
    public void delete_shouldNotThrowException_givenInvalidId() {
        // Arrange
        Long id = 99L;
        doNothing().when(userRepository).deleteById(id);

        // Act & Assert
        assertDoesNotThrow(() -> userServiceUnderTest.delete(id));
        verify(userRepository, times(1)).deleteById(id);
    }


    @DisplayName("deleteById with negative ID test")
    @Test
    public void delete_shouldNotThrowException_givenNegativeId() {
        // Arrange
        Long id = -1L;
        doNothing().when(userRepository).deleteById(id);

        // Act & Assert
        assertDoesNotThrow(() -> userServiceUnderTest.delete(id));
        verify(userRepository, times(1)).deleteById(id);
    }
}
