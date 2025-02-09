package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Tag("TeacherMapper Unit tests")
@SpringBootTest
public class TeacherMapperTest {

    @Autowired
    private TeacherMapper teacherMapperUnderTest;

    TeacherDto teacherDto;
    Teacher teacher;


    @BeforeEach
    public void setup() {

        teacherDto = new TeacherDto(1L, "Teacher", "Teacher",  LocalDateTime.now(),  LocalDateTime.now());
        teacher = Teacher.builder()
                .id(1L)
                .firstName("Teacher")
                .lastName("Teacher")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    @Tag("shouldMapDtoToEntity valid unit test")
    @Test
    public void shouldMapDtoToEntity() {
        Teacher result = teacherMapperUnderTest.toEntity(teacherDto);

        assert(result.equals(teacher));
        assert(result.getFirstName().equals(teacher.getFirstName()));
        assert(result.getLastName().equals(teacher.getLastName()));
        assert(result.getCreatedAt().equals(teacher.getCreatedAt()));
        assert(result.getUpdatedAt().equals(teacher.getUpdatedAt()));
    }

    @Tag("shouldMapListDtoToListEntity valid unit test")

    @Test
    public void shouldMapListDtoToListEntity() {
        List<Teacher> result = teacherMapperUnderTest.toEntity(Collections.singletonList(teacherDto));

        assert(result.equals(Collections.singletonList(teacher)));
    }

    @Tag("shouldMapEntityToDto valid unit test")
    @Test
    public void shouldMapEntityToDto() {
        TeacherDto result = teacherMapperUnderTest.toDto(teacher);

        assert(result.equals(teacherDto));
        assert(result.getFirstName().equals(teacherDto.getFirstName()));
        assert(result.getLastName().equals(teacherDto.getLastName()));
        assert(result.getCreatedAt().equals(teacherDto.getCreatedAt()));
        assert(result.getUpdatedAt().equals(teacherDto.getUpdatedAt()));
    }

    @Disabled
    @Tag("shouldMapListEntityToListDto valid unit test")
    @Test
    public void shouldMapListEntityToListDto() {
        List<TeacherDto> result = teacherMapperUnderTest.toDto(Collections.singletonList(teacher));

        assert(result.equals(Collections.singletonList(teacherDto)));
    }

}
