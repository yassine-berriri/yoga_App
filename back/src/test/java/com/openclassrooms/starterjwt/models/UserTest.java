package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("user unit tests")
public class UserTest {

    @DisplayName("hashCode valid unit test")
    @Test
    public void hashCode_shouldBeEquals_GivenTwoUsersWithSameId(){
        User user1 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .firstName("user1")
                .lastName("user1")
                .password("user1")
                .build();
        User user2 = User.builder()
                .id(1L)
                .email("user2@gmail.com")
                .firstName("user2")
                .lastName("user2")
                .password("user2")
                .build();

        assertTrue(user1.hashCode() == user2.hashCode());
    }    @DisplayName("hashCode valid unit test")
    @Test
    public void hashCode_shouldNotBeEquals_GivenTwoUsersWithSameId(){
        User user1 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .firstName("user1")
                .lastName("user1")
                .password("user1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .firstName("user2")
                .lastName("user2")
                .password("user2")
                .build();

        assertFalse(user1.hashCode() == user2.hashCode());
    }

}
