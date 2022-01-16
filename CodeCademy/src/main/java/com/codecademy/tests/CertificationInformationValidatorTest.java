package com.codecademy.tests;

import com.codecademy.informationhandling.validators.CertificateInformationValidator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CertificationInformationValidatorTest {

    public CertificationInformationValidatorTest() {
    }

    // Score
    @Test
    public void testIsValidScoreRequiresNegativeNumberEnsuresFalse() {
        // Arrange
        CertificateInformationValidator certificateInformationValidator = new CertificateInformationValidator();

        // Act
        int number = -5;
        boolean result = certificateInformationValidator.isValidScore(number);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidScoreRequiresElevenEnsuresFalse() {
        // Arrange
        CertificateInformationValidator certificateInformationValidator = new CertificateInformationValidator();

        // Act
        int number = 11;
        boolean result = certificateInformationValidator.isValidScore(number);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidScoreRequiresZeroEnsuresFalse() {
        // Arrange
        CertificateInformationValidator certificateInformationValidator = new CertificateInformationValidator();

        // Act
        int number = 0;
        boolean result = certificateInformationValidator.isValidScore(number);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidScoreRequiresOneEnsuresTrue() {
        // Arrange
        CertificateInformationValidator certificateInformationValidator = new CertificateInformationValidator();

        // Act
        int number = 1;
        boolean result = certificateInformationValidator.isValidScore(number);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidScoreRequiresNineEnsuresTrue() {
        // Arrange
        CertificateInformationValidator certificateInformationValidator = new CertificateInformationValidator();

        // Act
        int number = 9;
        boolean result = certificateInformationValidator.isValidScore(number);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidScoreRequiresSixEnsuresTrue() {
        // Arrange
        CertificateInformationValidator certificateInformationValidator = new CertificateInformationValidator();

        // Act
        int number = 6;
        boolean result = certificateInformationValidator.isValidScore(number);

        // Assert
        assertTrue(result);
    }
}
