package com.openclassrooms.starterjwt.services;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

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
    public void create_shouldCreateUser_GivenSession(){
        //Arrange
        when(sessionRepository.save(sessionMock)).thenReturn(sessionMock);

        //Act
        Session result = sessionServiceUnderTest.create(sessionMock);

        //Assert
        assertThat(result).isEqualTo(sessionMock);
    }



}
