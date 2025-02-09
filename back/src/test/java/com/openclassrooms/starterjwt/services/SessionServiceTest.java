package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("SessionService Unit tests")
@SpringBootTest
public class SessionServiceTest {

    private SessionService sessionServiceUnderTest;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    User userMock;
    List<User> userListMock;
    Teacher teacherMock;
    Session sessionMock;

    @BeforeEach
    public void setup() {
        sessionServiceUnderTest = new SessionService(sessionRepository, userRepository);

        userMock = User.builder()
                .id(1L)
                .email("yassine@gmail.com")
                .admin(true)
                .firstName("yassine")
                .lastName("yassine")
                .password("password")
                .build();

        teacherMock = Teacher.builder()
                .id(1L)
                .lastName("teacher")
                .firstName("teacher")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userListMock = new ArrayList<>();
        userListMock.add(userMock);

        sessionMock = Session.builder()
                .id(1L)
                .description("description")
                .name("session")
                .date(new Date())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .teacher(teacherMock)
                .users(userListMock)
                .build();
    }

    @DisplayName("Delete session valid unit test")
    @Test
    public void delete_shouldDeleteSession_GivenValidId() {
        // Arrange
        doNothing().when(sessionRepository).deleteById(1L);

        // Act
        sessionServiceUnderTest.delete(1L);

        // Assert
        verify(sessionRepository, times(1)).deleteById(1L);
    }

    @DisplayName("Delete session invalid unit test")
    @Test
    public void delete_shouldNotThrowException_GivenInvalidId() {
        // Arrange
        doNothing().when(sessionRepository).deleteById(99L);

        // Act & Assert
        assertDoesNotThrow(() -> sessionServiceUnderTest.delete(99L));
    }

    @DisplayName("Find session by ID valid unit test")
    @Test
    public void getById_shouldReturnSession_GivenValidId() {
        // Arrange
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(sessionMock));

        // Act
        Session result = sessionServiceUnderTest.getById(1L);

        // Assert
        assertThat(result).isEqualTo(sessionMock);
    }

    @DisplayName("Find session by ID invalid unit test")
    @Test
    public void getById_shouldReturnNull_GivenInvalidId() {
        // Arrange
        when(sessionRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Session result = sessionServiceUnderTest.getById(99L);

        // Assert
        assertNull(result);
    }

    @DisplayName("Find all sessions when sessions exist")
    @Test
    public void findAll_shouldReturnSessionList_GivenExistingSessions() {
        // Arrange
        List<Session> sessions = Arrays.asList(sessionMock);
        when(sessionRepository.findAll()).thenReturn(sessions);

        // Act
        List<Session> result = sessionServiceUnderTest.findAll();

        // Assert
        assertThat(result).isEqualTo(sessions);
    }




    @DisplayName("Participate in session with non-existent session")
    @Test
    public void participate_shouldThrowNotFoundException_GivenNonExistentSession() {
        // Arrange
        when(sessionRepository.findById(99L)).thenReturn(Optional.empty());

        // Assert
        assertThrowsExactly(NotFoundException.class, () -> sessionServiceUnderTest.participate(99L, 1L));
    }

    @DisplayName("No longer participate in session with non-existent session")
    @Test
    public void noLongerParticipate_shouldThrowNotFoundException_GivenNonExistentSession() {
        // Arrange
        when(sessionRepository.findById(99L)).thenReturn(Optional.empty());

        // Assert
        assertThrowsExactly(NotFoundException.class, () -> sessionServiceUnderTest.noLongerParticipate(99L, 1L));
    }
}
