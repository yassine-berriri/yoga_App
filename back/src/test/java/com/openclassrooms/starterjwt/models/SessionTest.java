package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertTrue(session1.hashCode() == session2.hashCode());
    }

    @DisplayName("hashCode valid unit test")
    @Test
    public void hashCode_shouldNotBeEquals_GivenTwoTeachersWithSameId(){
        Session session1 = Session.builder()
                .id(1L)
                .name("session1")
                .build();
        Session session2 = Session.builder()
                .id(2L)
                .name("session2")
                .build();

        assertFalse(session1.hashCode() == session2.hashCode());
    }
}
