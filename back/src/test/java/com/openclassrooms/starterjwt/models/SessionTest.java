package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    @DisplayName("hashCode valid unit test")
    @Test
    public void hashCode_shouldBeEquals_GivenTwoSessionsWithSameId(){
        Session session1 = Session.builder()
                .id(1L)
                .name("session1")
                .build();
        Session session2 = Session.builder()
                .id(1L)
                .name("session2")
                .build();

        assertEquals(session1.hashCode(), session2.hashCode());
    }

    @DisplayName("hashCode invalid unit test")
    @Test
    public void hashCode_shouldNotBeEquals_GivenTwoSessionsWithDifferentId(){
        Session session1 = Session.builder()
                .id(1L)
                .name("session1")
                .build();
        Session session2 = Session.builder()
                .id(2L)
                .name("session2")
                .build();

        assertNotEquals(session1.hashCode(), session2.hashCode());
    }

    @DisplayName("equals method test with same ID")
    @Test
    public void equals_shouldReturnTrue_GivenTwoSessionsWithSameId(){
        Session session1 = Session.builder()
                .id(1L)
                .name("session1")
                .build();
        Session session2 = Session.builder()
                .id(1L)
                .name("session2")
                .build();

        assertTrue(session1.equals(session2));
    }

    @DisplayName("equals method test with different IDs")
    @Test
    public void equals_shouldReturnFalse_GivenTwoSessionsWithDifferentId(){
        Session session1 = Session.builder()
                .id(1L)
                .name("session1")
                .build();
        Session session2 = Session.builder()
                .id(2L)
                .name("session2")
                .build();

        assertFalse(session1.equals(session2));
    }

    @DisplayName("Validation test: should fail for missing required fields")
    @Test
    public void validation_shouldFail_GivenMissingRequiredFields(){
        Session session = new Session(); // Aucun champ renseigné

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Session>> violations = validator.validate(session);

        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size()); // name, date et description sont obligatoires
    }

    @DisplayName("Validation test: should pass with valid fields")
    @Test
    public void validation_shouldPass_GivenValidFields(){
        Session session = Session.builder()
                .name("Maths Advanced")
                .date(new Date())
                .description("This is a description of the session")
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Session>> violations = validator.validate(session);

        assertTrue(violations.isEmpty());
    }

    @DisplayName("Test teacher association")
    @Test
    public void session_shouldContainTeacher_WhenTeacherIsAssigned(){
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        Session session = Session.builder()
                .id(1L)
                .name("Science Class")
                .teacher(teacher)
                .build();

        assertNotNull(session.getTeacher());
        assertEquals(1L, session.getTeacher().getId());
    }

}
