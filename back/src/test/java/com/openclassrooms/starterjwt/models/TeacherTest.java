package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TeacherTest {

    @DisplayName("hashCode valid unit test")
    @Test
    public void hashCode_shouldBeEquals_GivenTwoTeachersWithSameId(){
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .firstName("user1")
                .lastName("user1")
                .build();
        Teacher teacher2 = Teacher.builder()
                .id(1L)
                .firstName("teacher2")
                .lastName("teacher2")
                .build();

        assertTrue(teacher1.hashCode() == teacher2.hashCode());
    }

    @DisplayName("hashCode valid unit test")
    @Test
    public void hashCode_shouldNotBeEquals_GivenTwoTeachersWithSameId(){
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .firstName("user1")
                .lastName("user1")
                .build();
        Teacher teacher2 = Teacher.builder()
                .id(2L)
                .firstName("teacher2")
                .lastName("teacher2")
                .build();

        assertFalse(teacher1.hashCode() == teacher2.hashCode());
    }
}
