package com.codecademy.tests;

import com.codecademy.informationhandling.validators.StudentInformationValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentInformationValidationTest {

    public StudentInformationValidationTest() {
    }

    // Email
    @Test
    public void testIsValidEmailRequiresNoAtSignEnsuresFalse() {
        // Arrange
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String email = "johndoe.nl";
        boolean result = studentInformationValidator.isValidEmail(email);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testisValidEmailRequiresNoMailBoxPartEnsuresFalse() {
        // Arrange
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String email = "@hotmail.nl";
        boolean result = studentInformationValidator.isValidEmail(email);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidEmailRequiresSubdomainTLDDelimiterEnsuresFalse() {
        // Arrange
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String email = "sophie.jackson8@hotmail";
        boolean result = studentInformationValidator.isValidEmail(email);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidEmailRequiresNoSubdomainPartEnsuresFalse() {
        // Arrange
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String email = "maria778@.nl";
        boolean result = studentInformationValidator.isValidEmail(email);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidEmailRequiresNoTLDPartEnsuresFalse() {
        // Arrange
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String email = "info@bottle.";
        boolean result = studentInformationValidator.isValidEmail(email);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidEmailRequiresValidEmailEnsuresTrue() {
        // Arrange
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String email = "peter.harvey@gmail.com";
        boolean result = studentInformationValidator.isValidEmail(email);

        // Assert
        assertTrue(result);
    }


    // Postal Code
    @Test
    public void testIsValidPostalCodeRequiresValidPostalCodeWithSpacesEnsuresTrue() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String postalCode = "    5882    GP   ";
        boolean result = studentInformationValidator.isValidPostalCode(postalCode);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidPostalCodeRequiresPostalCodeWithNoSpacesEnsuresTrue() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String postalCode = "8639XZ";
        boolean result = studentInformationValidator.isValidPostalCode(postalCode);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidPostalCodeRequiresValidPostalCodeEnsuresTrue() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String postalCode = "3158 PE";
        boolean result = studentInformationValidator.isValidPostalCode(postalCode);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidPostalCodeRequiresInvalidPostalCodeEnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String postalCode = "353 GPR";
        boolean result = studentInformationValidator.isValidPostalCode(postalCode);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidPostalCodeRequiresOutOfRangePostalCodeEnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String postalCode = "0568 BG";
        boolean result = studentInformationValidator.isValidPostalCode(postalCode);

        // Assert
        assertFalse(result);
    }

    // Birthday
    @Test
    public void testIsValidBirthdayRequiresDay30Month2EnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "30", "2", "2002" };
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidBirthdayRequiresDay0EnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "0", "1", "2000"};
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidBirthdayRequiresDay15Month15EnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "15", "15", "2000"};
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidBirthdayRequiresNegativeYearEnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "25", "10", "-164" };
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidBirthdayRequiresDay18Month5Year2021EnsuresTrue() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "18", "5", "2021" };
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidBirthdayRequiresDay29Month2NoLeapYearEnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "29", "2", "3005" };
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidBirthdayRequiresDay29Month2LeapYearEnsureTrue() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "29", "2", "2004" };
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidBirthdayRequiresDay32EnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "32", "2", "2021" };
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidBirthdayRequiresDay15Month13EnsuresFalse() {
        // Assert
        StudentInformationValidator studentInformationValidator = new StudentInformationValidator();

        // Act
        String[] birthdayPieces = new String[]{ "15", "13", "2021" };
        boolean result = studentInformationValidator.isValidBirthday(birthdayPieces);

        // Assert
        assertFalse(result);
    }
}
