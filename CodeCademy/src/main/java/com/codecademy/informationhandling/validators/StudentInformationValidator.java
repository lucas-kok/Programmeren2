package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.student.Student;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.regex.Pattern;

public class StudentInformationValidator {

    public StudentInformationValidator() {

    }

    // Function to validate all inputs for a new Student and returns an error/empty message
    public String validateNewStudent(String name, String email, String postalCode, String[] birthday, Map<String, Student> students) {
        StringBuilder message = new StringBuilder();

        if (!validateName(name)) message.append("\nPlease fill in the First and Last name!");
        if (!validateMailAddress(email)) message.append("\nThe email: '").append(email).append("' is not valid!");
        if (!mailAddressDoesNotExist(email, students))
            message.append("\nThe email: ").append(email).append(" already exists!");
        if (!validatePostalCode(postalCode))
            message.append("\nThe postal-code: '").append(postalCode).append("' is not valid!");
        if (validateBirthday(birthday)) {
            if (!validateAge(birthday)) message.append("\nThe person is not old enough!");
        } else {
            message.append("The given date is not valid!");
        }

        return message.toString();
    }

    // Function to validate all inputs for an existing Student and returns an error/empty message
    public String validateEditedStudent(String name, String email, String postalCode, String[] birthday, Map<String, Student> students, Student selectedStudent) {
        StringBuilder message = new StringBuilder();

        if (!validateName(name)) message.append("\nPlease fill in both First- and Last name!");
        if (!validateMailAddress(email)) message.append("\nThe email: '").append(email).append("' is not valid!");
        if (!email.equals(selectedStudent.getEmail())) { // Student already has his own email (Already exists, but still valid)
            if (!mailAddressDoesNotExist(email, students))
                message.append("\nThe email: ").append(email).append(" already exists!");
        }
        if (!validatePostalCode(postalCode))
            message.append("\nThe postal-code: '").append(postalCode).append("' is not valid!");
        if (validateBirthday(birthday)) {
            if (!validateAge(birthday)) message.append("\nThe person is not old enough!");
        } else {
            message.append("The given date is not valid!");
        }

        return message.toString();
    }

    // Name
    public boolean validateName(String name) {
        return name.split(" ").length >= 2;
    }

    // Email
    public boolean validateMailAddress(String mailAddress) {
        String emailFormat = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailFormat);
        return pat.matcher(mailAddress).matches();
    }

    public boolean mailAddressDoesNotExist(String mailAddress, Map<String, Student> students) {
        return students.get(mailAddress) == null;
    }

    // PostalCode
    public boolean validatePostalCode(String postalCode) {
        if (postalCode.isBlank()) return false;

        postalCode = postalCode.replaceAll(" ", "");
        return postalCode.matches("[1-9][0-9]{3}[a-zA-Z]{2}");
    }

    // Age
    public boolean validateBirthday(String[] birthday) {
        int day = Integer.parseInt(birthday[0]);
        int month = Integer.parseInt(birthday[1]);
        int year = Integer.parseInt(birthday[2]);

        if ((day < 1 || day > 31) || (month < 1 || month >= 12)) return false;

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day >= 1 && day <= 31) return true;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day <= 30) return true;
        } else if (month == 2) {
            if (year % 4 == 0) {
                if (!(year % 100 == 0 && year % 400 != 0)) {
                    if (day <= 29) return true;
                }
            }
            if (day <= 28) return true;
        }
        return false;
    }

    public boolean validateAge(String[] birthdayPieces) {
//        String day = String.format("%02d", birthdayPieces[0]);
//        String month = String.format("%02d", birthdayPieces[1]);
        String day = birthdayPieces[0];
        String month = birthdayPieces[1];
        String year = birthdayPieces[2];

        LocalDate studentBirthday = LocalDate.parse(year + "-" + month + "-" + day);
        LocalDate today = LocalDate.now();
        Period period = Period.between(studentBirthday, today);
        int minimumAge = 10; // Minimum age of codecademy is 10 years

        return period.getYears() >= minimumAge;
    }

}
