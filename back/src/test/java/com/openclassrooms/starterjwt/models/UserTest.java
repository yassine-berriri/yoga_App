package com.openclassrooms.starterjwt.models;

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

@Tag("user unit tests")
public class UserTest {

    private final Validator validator;

    public UserTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @DisplayName("hashCode should be equal for two users with same id")
    @Test
    public void hashCode_shouldBeEquals_GivenTwoUsersWithSameId() {
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

        assertEquals(user1.hashCode(), user2.hashCode(), "HashCode should be equal for users with the same ID.");
    }

    @DisplayName("hashCode should not be equal for two users with different id")
    @Test
    public void hashCode_shouldNotBeEquals_GivenTwoUsersWithDifferentId() {
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

        assertNotEquals(user1.hashCode(), user2.hashCode(), "HashCode should not be equal for users with different IDs.");
    }

    @DisplayName("equals should return true for users with same id")
    @Test
    public void equals_shouldReturnTrue_GivenUsersWithSameId() {
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


        assertEquals(user1, user2, "Users with the same ID should be equal.");
    }

    @DisplayName("equals should return false for users with different id")
    @Test
    public void equals_shouldReturnFalse_GivenUsersWithDifferentId() {
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


        assertNotEquals(user1, user2, "Users with different IDs should not be equal.");
    }

    @DisplayName("toString should contain all fields")
    @Test
    public void toString_ShouldContainAllFields() {
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        String toString = user.toString();
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
        assertTrue(toString.contains("password123"));
        assertTrue(toString.contains("admin=true"));
    }

    @DisplayName("User should be created with all required fields")
    @Test
    public void user_ShouldBeCreated_WithValidFields() {
        User user = new User("test@example.com", "Doe", "John", "password123", true);
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("password123", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @DisplayName("User should support method chaining with Accessors(chain = true)")
    @Test
    public void user_ShouldSupportMethodChaining() {
        User user = new User()
                .setEmail("user@example.com")
                .setFirstName("Jane")
                .setLastName("Doe")
                .setPassword("securepassword")
                .setAdmin(true);

        assertEquals("user@example.com", user.getEmail());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("securepassword", user.getPassword());
        assertTrue(user.isAdmin());
    }

    @DisplayName("User should not be valid with an invalid email")
    @Test
    public void user_ShouldNotBeValid_WithInvalidEmail() {
        User user = new User("invalid-email", "Doe", "John", "password123", true);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Validation should fail for an invalid email.");
    }

}