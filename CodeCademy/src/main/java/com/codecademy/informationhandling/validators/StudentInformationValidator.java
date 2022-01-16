package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.student.Student;
import com.codecademy.informationhandling.student.StudentRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.regex.Pattern;

public class StudentInformationValidator {
    private final StudentRepository studentRepository;

    public StudentInformationValidator() {
        studentRepository = new StudentRepository();
    }

    // Function to validate all inputs for a new Student and returns an error/empty message
    public String validateNewStudent(String studentName, String email, String address, String postalCode, String[] birthday) throws SQLException {
        StringBuilder message = new StringBuilder();

        if (!isValidName(studentName)) {
            message.append("\nPlease fill in the First and Last name!");
        }

        if (!isValidEmail(email)) {
            message.append("\nThe email: '").append(email).append("' is not valid!");
        }

        if (emailExists(email)) {
            message.append("\nThe email: '").append(email).append("' already exists!");
        }

        if (!isValidAddress(address)) {
            message.append("\nThe address: '").append(address).append("' is not valid!");
        }

        if (!isValidPostalCode(postalCode)) {
            message.append("\nThe postal-code: '").append(postalCode).append("' is not valid!");
        }

        if (isValidBirthday(birthday)) {
            if (!isValidAge(birthday)) message.append("\nThe person is not old enough!");
        } else {
            message.append("\nThe given date is not valid!");
        }

        return message.toString();
    }

    // Function to validate all inputs for an existing Student and returns an error/empty message
    public String validateEditedStudent(String studentName, String email, String address, String postalCode, String[] birthday, Student selectedStudent) throws SQLException {
        StringBuilder message = new StringBuilder();

        if (!isValidName(studentName)) {
            message.append("\nPlease fill in both First- and Last name!");
        }

        if (!isValidEmail(email)) {
            message.append("\nThe email: '").append(email).append("' is not valid!");
        }

        if (!email.equals(selectedStudent.getEmail())) { // Student has his own email (Already exists, but still valid)
            if (emailExists(email)) message.append("\nThe email: ").append(email).append(" already exists!");
        }

        if (!isValidAddress(address)) {
            message.append("\nThe address: '").append(address).append("' is not valid!");
        }

        if (!isValidPostalCode(postalCode)) {
            message.append("\nThe postal-code: '").append(postalCode).append("' is not valid!");
        }

        if (isValidBirthday(birthday)) {
            if (!isValidAge(birthday)) message.append("\nThe person is not old enough!");
        } else {
            message.append("\nThe given date is not valid!");
        }

        return message.toString();
    }

    // Name
    public boolean isValidName(String studentName) {
        return studentName.split(" ").length >= 2;
    }

    // Email
    public boolean isValidEmail(String mailAddress) {
        String emailFormat = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailFormat);
        return pat.matcher(mailAddress).matches();
    }

    public boolean emailExists(String mailAddress) throws SQLException {
        Map<String, Student> students = studentRepository.getAllStudents();
        return students.containsKey(mailAddress);
    }

    // Address
    private boolean isValidAddress(String address) {
        char[] chars = address.toCharArray();

        for(char c : chars){
            if(Character.isDigit(c)){
                return true;
            }
        }

        return false;
    }

    // PostalCode
    public boolean isValidPostalCode(String postalCode) {
        postalCode = postalCode.replaceAll(" ", "");
        return postalCode.matches("[1-9][0-9]{3}[a-zA-Z]{2}");
    }

    // Age
    public boolean isValidBirthday(String[] birthday) {
        if (!isValidNumber(birthday[0]) || !isValidNumber(birthday[1]) || !isValidNumber(birthday[2])) return false;

        int day = Integer.parseInt(birthday[0]);
        int month = Integer.parseInt(birthday[1]);
        int year = Integer.parseInt(birthday[2]);

        if (year < 1000) return false;

        if (day < 1 || day > 31 || month < 1 || month > 12) return false;

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return true;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day <= 30;
        } else {
            if (year % 4 == 0) {
                if (!(year % 100 == 0 && year % 400 != 0)) {
                    if (day <= 29) return true;
                }
            }
            return day <= 28;
        }
    }

    public boolean isValidAge(String[] birthdayPieces) {
        String day = birthdayPieces[0];
        String month = birthdayPieces[1];
        String year = birthdayPieces[2];

        if (day.length() < 2)
            day = Integer.parseInt(birthdayPieces[0]) < 10 ? "0" + birthdayPieces[0] : birthdayPieces[0]; // "0" -> "01"
        if (month.length() < 2)
            month = Integer.parseInt(birthdayPieces[1]) < 10 ? "0" + birthdayPieces[1] : birthdayPieces[1];

        LocalDate studentBirthday = LocalDate.parse(year + "-" + month + "-" + day);
        LocalDate today = LocalDate.now();
        Period period = Period.between(studentBirthday, today);
        int minimumAge = 10; // Minimum age of codecademy is 10 years

        return period.getYears() >= minimumAge;
    }

    private boolean isValidNumber(String scoreString) {
        try {
            Integer.parseInt(scoreString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
