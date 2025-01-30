package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@Tag("TeacherService Unit Tests")
@SpringBootTest
public class TeacherServiceTest {

    private TeacherService teacherServiceUnderTest;

    @Mock
    private TeacherRepository teacherRepository;


    Teacher teacherMock;

    List<Teacher> teachersListMock;

    @BeforeEach
    public void setup() {
        teacherServiceUnderTest = new TeacherService(teacherRepository);

        teacherMock = teacherMock.builder()
                .id(1L)
                .lastName("teacher")
                .firstName("teacher")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        teachersListMock = new ArrayList<>();
        teachersListMock.add(teacherMock);
    }

    @DisplayName("findAll valid test")
    @Test
    public void findAll_shouldFindAllTeachers() {
        // Arrange
        when(teacherRepository.findAll()).thenReturn(teachersListMock);

        // Act
        List<Teacher> result = teacherServiceUnderTest.findAll();

        // Assert
        assertThat(result).isEqualTo(teachersListMock);
    }

    @DisplayName("findTeacherById valid test")
    @Test
    public void findById_shouldFindTeacher_GivenId() {
        // Arrange
        Long id = 1L;
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacherMock));

        // Act
        Teacher result = teacherServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isEqualTo(teacherMock);
    }

    @DisplayName("findTeacherById inValid test")
    @Test
    public void findById_shouldReturnNull_GivenId() {

        // Arrange
        Long id = 1L;
        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Teacher result = teacherServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isEqualTo(null);
    }



}
