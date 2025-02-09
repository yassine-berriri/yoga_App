package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTest {

    private final Validator validator;

    public SignupRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testSignupRequestWithShortLastName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("D"); // Trop court
        signupRequest.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty(), "There should be a violation for a short last name.");
        assertEquals(1, violations.size());

        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertEquals("lastName", violation.getPropertyPath().toString());
        assertEquals("la taille doit être comprise entre 3 et 20", violation.getMessage());
    }

    @Test
    public void testSignupRequestWithLongLastName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("ThisLastNameIsWayTooLongToBeValid"); // Trop long
        signupRequest.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty(), "There should be a violation for a long last name.");
        assertEquals(1, violations.size());

        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertEquals("lastName", violation.getPropertyPath().toString());
        assertEquals("la taille doit être comprise entre 3 et 20", violation.getMessage());
    }

    @Test
    public void testSignupRequestWithShortPassword() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("123"); // Trop court

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty(), "There should be a violation for a short password.");
        assertEquals(1, violations.size());

        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertEquals("password", violation.getPropertyPath().toString());
        assertEquals("la taille doit être comprise entre 6 et 40", violation.getMessage());
    }

    @Test
    public void testSignupRequestWithNullEmail() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(null); // Null email
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty(), "There should be a violation for a null email.");
        assertEquals(1, violations.size());

        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("ne doit pas être vide", violation.getMessage());
    }



    @Test
    public void testSignupRequestWithPasswordContainingSpaces() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("  password123  "); // Espaces autour

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertTrue(violations.isEmpty(), "Whitespace at the beginning or end should not cause validation failure.");
    }

    @Test
    public void testSignupRequestWithSpecialCharacterEmail() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid.email+tag@example.co.uk"); // Email valide avec caractères spéciaux
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertTrue(violations.isEmpty(), "There should be no violations for a valid special character email.");
    }

    @Test
    public void testSignupRequestWithBoundaryValidFields() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("a@b.co"); // Email valide minimaliste
        signupRequest.setFirstName("Ana"); // Nom valide au minimum de 3 caractères
        signupRequest.setLastName("Li"); // Nom trop court, mais limite de 3 caractères
        signupRequest.setPassword("123456"); // Mot de passe au minimum de 6 caractères

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        assertFalse(violations.isEmpty(), "There should be a violation for the lastName being too short.");
    }


    private SignupRequest createSampleSignupRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");
        return signupRequest;
    }

    @Test
    public void testToString_ShouldReturnLombokGeneratedFormat() {
        // Arrange
        SignupRequest signupRequest = createSampleSignupRequest();

        // Act
        String actualToString = signupRequest.toString();

        // Assert
        assertNotNull(actualToString);
        assertTrue(actualToString.contains("SignupRequest"));
        assertTrue(actualToString.contains("email=test@example.com"));
        assertTrue(actualToString.contains("firstName=John"));
        assertTrue(actualToString.contains("lastName=Doe"));
        assertTrue(actualToString.contains("password=password123"));
    }

    @Test
    public void testEquals_ShouldReturnTrue_WhenObjectsAreIdentical() {
        // Arrange
        SignupRequest signupRequest1 = createSampleSignupRequest();
        SignupRequest signupRequest2 = createSampleSignupRequest();

        // Act & Assert
        assertEquals(signupRequest1, signupRequest2);
    }

    @Test
    public void testEquals_ShouldReturnFalse_WhenObjectsAreDifferent() {
        // Arrange
        SignupRequest signupRequest1 = createSampleSignupRequest();
        SignupRequest signupRequest2 = new SignupRequest();
        signupRequest2.setEmail("different@example.com");
        signupRequest2.setFirstName("Jane");
        signupRequest2.setLastName("Smith");
        signupRequest2.setPassword("differentPassword");

        // Act & Assert
        assertNotEquals(signupRequest1, signupRequest2);
    }

    @Test
    public void testEquals_ShouldReturnFalse_WhenComparedWithNull() {
        // Arrange
        SignupRequest signupRequest = createSampleSignupRequest();

        // Act & Assert
        assertNotEquals(signupRequest, null);
    }

    @Test
    public void testEquals_ShouldReturnFalse_WhenComparedWithDifferentClass() {
        // Arrange
        SignupRequest signupRequest = createSampleSignupRequest();
        Object differentObject = new Object();

        // Act & Assert
        assertNotEquals(signupRequest, differentObject);
    }

    @Test
    public void testCanEqual_ShouldReturnTrue_WhenComparingSameClass() {
        // Arrange
        SignupRequest signupRequest1 = createSampleSignupRequest();
        SignupRequest signupRequest2 = createSampleSignupRequest();

        // Act
        boolean canEqual = signupRequest1.canEqual(signupRequest2);

        // Assert
        assertTrue(canEqual);
    }

    @Test
    public void testCanEqual_ShouldReturnFalse_WhenComparingDifferentClass() {
        // Arrange
        SignupRequest signupRequest = createSampleSignupRequest();
        Object differentObject = new Object();

        // Act
        boolean canEqual = signupRequest.canEqual(differentObject);

        // Assert
        assertFalse(canEqual);
    }



}
