package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
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

        userMock = userMock.builder()
                .id(1L)
                .email("yassine@gmail.com")
                .admin(true)
                .firstName("yassine")
                .lastName("yassine")
                .password("password")
                .build();

        teacherMock = teacherMock.builder()
                .id(1L)
                .lastName("teacher")
                .firstName("teacher")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userListMock = new ArrayList<>();
        userListMock.add(userMock);

        sessionMock = sessionMock.builder()
                .id(1L)
                .description("description")
                .name("session")
                .date(new Date())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .teacher(teacherMock)
                .users(userListMock).build();
    }

    @DisplayName("create session unit test")
    @Test
    public void create_shouldCreateSession_GivenSession(){
        //Arrange
        when(sessionRepository.save(sessionMock)).thenReturn(sessionMock);

        //Act
        Session result = sessionServiceUnderTest.create(sessionMock);

        //Assert
        assertThat(result).isEqualTo(sessionMock);
    }

    @DisplayName("update session unit test")
    @Test
    public void update_shouldUpdateSession_GivenSession(){
        //Arrange
        when(sessionRepository.save(sessionMock)).thenReturn(sessionMock);

        //Act
        Session result = sessionServiceUnderTest.update(1L,sessionMock);

        //Assert
        assertThat(result).isEqualTo(sessionMock);
    }

    @DisplayName("participate session invalid unit test")
    @Test
    public void participate_shouldThrowNotFoundException_GivenUserNull(){
        //Arrange
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());


        //Assert
        assertThrowsExactly(NotFoundException.class, () -> sessionServiceUnderTest.participate(1L, 1L));

    }

    @DisplayName("participate session invalid unit test Given Participated user")
    @Test
    public void participate_shouldThrowBadRequest_GivenParticipatedUser(){
        //Arrange
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(sessionMock));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userMock));

        //Assert
        assertThrowsExactly(BadRequestException.class, () -> sessionServiceUnderTest.participate(1L,1L));
    }

    @DisplayName("participate in session valid unit test")
    @Test
    public void participate_shouldParticipateSession_GivenUserIdAndSessionId(){

        //Arrange
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(sessionMock));
        when(userRepository.findById(2L)).thenReturn(Optional.of(userMock));

        //Act
        sessionServiceUnderTest.participate(1L,2L);

        //Assert
        verify(sessionRepository, times(1)).save(sessionMock);
    }


    @DisplayName("no longer participate in session invalid unit test")
    @Test
    public void noLongerParticipate_shouldThrowBadRequestException_GivenNoUserParticipate(){

        //Arrange
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(sessionMock));

        //Assert
        assertThrowsExactly(BadRequestException.class, () -> sessionServiceUnderTest.noLongerParticipate(1L, 2L));

    }

    @DisplayName("no longer participate in session valid unit test")
    @Test
    public void noLongerParticipate_shouldSaveSession_givenIdAndUserId() {

        //Arrange
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(sessionMock));

        //Act
        sessionServiceUnderTest.noLongerParticipate(1L, 1L);

        //Assert
        verify(sessionRepository, times(1)).save(sessionMock);
    }


}
