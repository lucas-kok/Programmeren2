package com.codecademy.tests;

import com.codecademy.informationhandling.validators.RegistrationInformationValidator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegistrationInformationValidatorTest {

    public RegistrationInformationValidatorTest() {
    }

    // Registration Date
    @Test
    public void testIsValidRegistrationDateRequiresDay30Month2EnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "30", "2", "2002" };
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidRegistrationDateRequiresDay0EnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "0", "1", "2000"};
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidRegistrationDateRequiresDay15Month15EnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "15", "15", "2000"};
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidRegistrationDateRequiresNegativeYearEnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "25", "10", "-164" };
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidRegistrationDateRequiresDay18Month5Year2021EnsuresTrue() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "18", "5", "2021" };
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidRegistrationDateRequiresDay29Month2NoLeapYearEnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "29", "2", "3005" };
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidRegistrationDateRequiresDay29Month2LeapYearEnsureTrue() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "29", "2", "2004" };
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidRegistrationDateRequiresDay32EnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "32", "2", "2021" };
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidRegistrationDateRequiresDay15Month13EnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        String[] registrationDatePieces = new String[]{ "15", "13", "2021" };
        boolean result = registrationInformationValidator.isValidRegistrationDate(registrationDatePieces);

        // Assert
        assertFalse(result);
    }

    // Progression
    @Test
    public void testIsValidPercentageRequiresNegativeNumberEnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(-5);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidPercentageRequiresMinusOneEnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(-1);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidPercentageRequiresOneEnsuresTrue() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(1);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidPercentageRequiresMinusTenEnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(-10);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsValidPercentageRequiresTenEnsuresTrue() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(10);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidPercentageRequiresZeroEnsuresTrue() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(0);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidPercentageRequiresHundredEnsuresTrue() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(100);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidPercentageRequiresNinetyNineEnsuresTrue() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(99);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsValidPercentageRequiresHundredOneEnsuresFalse() {
        // Arrange
        RegistrationInformationValidator registrationInformationValidator = new RegistrationInformationValidator();

        // Act
        ArrayList<Integer> number = new ArrayList<>();
        number.add(101);
        boolean result = registrationInformationValidator.isValidProgression(number);

        // Assert
        assertFalse(result);
    }
}
