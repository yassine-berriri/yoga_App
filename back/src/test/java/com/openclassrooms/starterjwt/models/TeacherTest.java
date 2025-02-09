package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tag("teacher unit tests")
public class TeacherTest {

    private final Validator validator;

    public TeacherTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    private Teacher teacher1;
    private Teacher teacher2;

    @BeforeEach
    public void setUp() {
        teacher1 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        teacher2 = Teacher.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("hashCode should be equal for teachers with the same ID")
    @Test
    public void hashCode_ShouldBeEqual_GivenTeachersWithSameId() {
        Teacher teacherDuplicate = Teacher.builder()
                .id(1L)
                .firstName("Another")
                .lastName("Teacher")
                .build();

        assertEquals(teacher1.hashCode(), teacherDuplicate.hashCode(), "HashCode should be equal for teachers with the same ID.");
    }

    @DisplayName("hashCode should not be equal for teachers with different IDs")
    @Test
    public void hashCode_ShouldNotBeEqual_GivenTeachersWithDifferentId() {
        assertNotEquals(teacher1.hashCode(), teacher2.hashCode(), "HashCode should not be equal for teachers with different IDs.");
    }

    @DisplayName("equals should return true for teachers with the same ID")
    @Test
    public void equals_ShouldReturnTrue_GivenTeachersWithSameId() {
        Teacher teacherDuplicate = Teacher.builder()
                .id(1L)
                .firstName("Another")
                .lastName("Teacher")
                .build();

        assertEquals(teacher1, teacherDuplicate, "Teachers with the same ID should be equal.");
    }

    @DisplayName("equals should return false for teachers with different IDs")
    @Test
    public void equals_ShouldReturnFalse_GivenTeachersWithDifferentId() {
        assertNotEquals(teacher1, teacher2, "Teachers with different IDs should not be equal.");
    }

    @DisplayName("toString should contain all fields")
    @Test
    public void toString_ShouldContainAllFields() {
        String toString = teacher1.toString();

        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("createdAt"));
        assertTrue(toString.contains("updatedAt"));
    }

    @DisplayName("Teacher should be created with all required fields")
    @Test
    public void teacher_ShouldBeCreated_WithValidFields() {
        teacher1 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        assertNotNull(teacher1);
        assertEquals("Doe", teacher1.getLastName());
        assertEquals("John", teacher1.getFirstName());
    }
}