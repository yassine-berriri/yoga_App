package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.util.DateUtil.now;

@Tag("SessionMapper unit tests")
@SpringBootTest
public class SessionMapperTest {
    @Autowired
    private SessionMapper sessionMapperUnderTest;

    SessionDto sessionDto;
    Session session;

    @BeforeEach
    public void init() {

        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("teacher")
                .lastName("teacher")
                .build();

        sessionDto = new SessionDto(1L, "Session", now(), 1L, "Session description", new ArrayList<>(), currentLocalDateTime, currentLocalDateTime);
        session = Session.builder()
                .id(1L)
                .name("Session")
                .date(now())
                .teacher(teacher)
                .description("Session description")
                .users(new ArrayList<>())
                .createdAt(currentLocalDateTime)
                .updatedAt(currentLocalDateTime)
                .build();
    }

    @Tag("shouldMapDtoToEntity valid unit test")
    @Test
    public void shouldMapDtoToEntity() {
        Session result = sessionMapperUnderTest.toEntity(sessionDto);

        assert(result.equals(session));
        assert(result.getId().equals(session.getId()));
        assert(result.getName().equals(session.getName()));
        assert(result.getTeacher().equals(session.getTeacher()));
        assert(result.getDescription().equals(session.getDescription()));
        assert(result.getUsers().equals(session.getUsers()));
        assert(result.getCreatedAt().equals(session.getCreatedAt()));
        assert(result.getUpdatedAt().equals(session.getUpdatedAt()));
    }

    @Tag("shouldMapListDtoToListEntity valid unit test")
    @Test
    public void shouldMapListDtoToListEntity() {
        List<Session> result = sessionMapperUnderTest.toEntity(Collections.singletonList(sessionDto));

        assert(result.equals(Collections.singletonList(session)));
    }

    @Tag("shouldMapEntityToDto valid unit test")
    @Test
    public void shouldMapEntityToDto() {
        SessionDto result = sessionMapperUnderTest.toDto(session);

        assert(result.equals(sessionDto));
        assert(result.getId().equals(sessionDto.getId()));
        assert(result.getName().equals(sessionDto.getName()));
        assert(result.getTeacher_id().equals(sessionDto.getTeacher_id()));
        assert(result.getDescription().equals(sessionDto.getDescription()));
        assert(result.getUsers().equals(sessionDto.getUsers()));
        assert(result.getCreatedAt().equals(sessionDto.getCreatedAt()));
        assert(result.getUpdatedAt().equals(sessionDto.getUpdatedAt()));
    }

    @Tag("shouldMapListEntityToListDto valid unit test")
    @Test
    public void shouldMapListEntityToListDto() {
        List<SessionDto> result = sessionMapperUnderTest.toDto(Collections.singletonList(session));

        assert(result.equals(Collections.singletonList(sessionDto)));
    }
}
