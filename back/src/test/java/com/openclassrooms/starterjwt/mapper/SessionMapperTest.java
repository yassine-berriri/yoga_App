package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("SessionMapper unit tests")
@SpringBootTest
public class SessionMapperTest {

    @Autowired
    private SessionMapper sessionMapperUnderTest;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    SessionDto sessionDto;
    Session session;
    Teacher teacher;

    @BeforeEach
    public void init() {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        teacher = Teacher.builder()
                .id(1L)
                .firstName("teacher")
                .lastName("teacher")
                .build();

        sessionDto = new SessionDto(1L, "Session", new Date(), 1L, "Session description", new ArrayList<>(), currentLocalDateTime, currentLocalDateTime);
        session = Session.builder()
                .id(1L)
                .name("Session")
                .date(new Date())
                .teacher(teacher)
                .description("Session description")
                .users(new ArrayList<>())
                .createdAt(currentLocalDateTime)
                .updatedAt(currentLocalDateTime)
                .build();
    }

    @Test
    @Tag("shouldHandleNullTeacherInDto")
    public void shouldHandleNullTeacherInDto() {
        sessionDto.setTeacher_id(null);

        Session result = sessionMapperUnderTest.toEntity(sessionDto);

        assertNull(result.getTeacher(), "Teacher should be null when teacher_id is null in DTO.");
    }

    @Test
    @Tag("shouldHandleEmptyUsersList")
    public void shouldHandleEmptyUsersList() {
        sessionDto.setUsers(Collections.emptyList());

        Session result = sessionMapperUnderTest.toEntity(sessionDto);

        assertNotNull(result.getUsers(), "Users list should not be null.");
        assertTrue(result.getUsers().isEmpty(), "Users list should be empty.");
    }

    @Test
    @Tag("shouldHandleNullUsersList")
    public void shouldHandleNullUsersList() {
        sessionDto.setUsers(null);

        Session result = sessionMapperUnderTest.toEntity(sessionDto);

        assertNotNull(result.getUsers(), "Users list should be initialized as empty if null in DTO.");
        assertTrue(result.getUsers().isEmpty(), "Users list should be empty.");
    }


    @Test
    @Tag("shouldHandleNullTeacherInSession")
    public void shouldHandleNullTeacherInSession() {
        session.setTeacher(null);

        SessionDto result = sessionMapperUnderTest.toDto(session);

        assertNull(result.getTeacher_id(), "Teacher ID should be null in DTO when session has no teacher.");
    }

    @Test
    @Tag("shouldHandleNullUsersInSession")
    public void shouldHandleNullUsersInSession() {
        session.setUsers(null);

        SessionDto result = sessionMapperUnderTest.toDto(session);

        assertNotNull(result.getUsers(), "Users list should not be null in DTO.");
        assertTrue(result.getUsers().isEmpty(), "Users list should be empty when session has null users.");
    }

    @Test
    @Tag("shouldMapEmptyListDtoToEmptyListEntity")
    public void shouldMapEmptyListDtoToEmptyListEntity() {
        List<Session> result = sessionMapperUnderTest.toEntity(Collections.emptyList());

        assertNotNull(result, "Result should not be null.");
        assertTrue(result.isEmpty(), "List should be empty when input list is empty.");
    }

    @Test
    @Tag("shouldMapEmptyListEntityToEmptyListDto")
    public void shouldMapEmptyListEntityToEmptyListDto() {
        List<SessionDto> result = sessionMapperUnderTest.toDto(Collections.emptyList());

        assertNotNull(result, "Result should not be null.");
        assertTrue(result.isEmpty(), "List should be empty when input list is empty.");
    }
}
