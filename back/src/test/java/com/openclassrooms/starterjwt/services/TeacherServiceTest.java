package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@Tag("TeacherService Unit Tests")
@SpringBootTest
public class TeacherServiceTest {

    private TeacherService teacherServiceUnderTest;

    @Mock
    private TeacherRepository teacherRepository;

    private Teacher teacherMock;
    private List<Teacher> teachersListMock;

    @BeforeEach
    public void setup() {
        teacherServiceUnderTest = new TeacherService(teacherRepository);

        teacherMock = Teacher.builder()
                .id(1L)
                .lastName("teacher")
                .firstName("teacher")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        teachersListMock = new ArrayList<>();
        teachersListMock.add(teacherMock);
    }

    @DisplayName("findAll valid test - should return all teachers")
    @Test
    public void findAll_shouldReturnAllTeachers() {
        // Arrange
        when(teacherRepository.findAll()).thenReturn(teachersListMock);

        // Act
        List<Teacher> result = teacherServiceUnderTest.findAll();

        // Assert
        assertThat(result).isEqualTo(teachersListMock);
    }


    @DisplayName("findById valid test - should find teacher given valid ID")
    @Test
    public void findById_shouldFindTeacher_givenValidId() {
        // Arrange
        Long id = 1L;
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacherMock));

        // Act
        Teacher result = teacherServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isEqualTo(teacherMock);
    }

    @DisplayName("findById invalid test - should return null when teacher not found")
    @Test
    public void findById_shouldReturnNull_whenTeacherNotFound() {
        // Arrange
        Long id = 99L;
        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Teacher result = teacherServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isNull();
    }


    @DisplayName("findById invalid test - should return null when ID is negative")
    @Test
    public void findById_shouldReturnNull_whenIdIsNegative() {
        // Arrange
        Long id = -1L;
        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Teacher result = teacherServiceUnderTest.findById(id);

        // Assert
        assertThat(result).isNull();
    }
}
