package com.codecademy.informationhandling;

import com.codecademy.informationhandling.course.Course;
import com.codecademy.informationhandling.student.Student;

public class InformationFormatter {

    public InformationFormatter() {

    }

    // Function to format the information of a Student globally
    public void formatStudent(Student student) {
        // Name
        String name = student.getName();
        student.setName(capitalizeString(name));

        // Email
        String email = student.getEmail();
        student.setEmail(email.toLowerCase());

        // Address
        String address = student.getAddress();
        student.setAddress(capitalizeString(address));

        // PostalCode
        String postalCode = student.getPostalCode();
        postalCode = postalCode.replaceAll(" ", "");
        postalCode = postalCode.substring(0,4) + " " + postalCode.substring(4).toUpperCase();

        student.setPostalCode(postalCode);

        // City
        String cityName = student.getCity();
        student.setCity(capitalizeString(cityName));

        // Country
        String countryName = student.getCountry();
        student.setCountry(capitalizeString(countryName));

    }

    // Function to format the information of a Course globally
    public void formatCourse(Course course) {
        // Name
        String name = course.getName();
        course.setName(capitalizeString(name));

        // Subject
        String subject = course.getSubject();
        course.setSubject(capitalizeString(subject));

        // IntroductionText
        String introductionText = course.getIntroductionText();
        introductionText = introductionText.replaceAll("\\s{2,}", " ").trim();
        course.setIntroductionText(introductionText);
    }

    // Function that will capitalize each first letter of each word in a String
    public String capitalizeString(String input) {
        StringBuilder capitalizedString = new StringBuilder();
        input = input.replaceAll("\\s{2,}", " ").trim();

        String[] inputPieces;
        if (input.contains("-")) { // Like West-Capetown
            inputPieces = input.split("-");
            for (int i = 0; i < inputPieces.length; i++) {
                String cityNamePiece = inputPieces[i];
                capitalizedString.append(cityNamePiece.substring(0,1).toUpperCase()).append(cityNamePiece.substring(1));

                if (i != inputPieces.length - 1) {
                    capitalizedString.append("-");
                }
            }
        } else {
            inputPieces = input.split(" ");
            for (int i = 0; i < inputPieces.length; i++) {
                String inputPiece = inputPieces[i];
                //System.out.println(inputPiece);
                capitalizedString.append(inputPiece.substring(0,1).toUpperCase()).append(inputPiece.substring(1));

                if (i != inputPieces.length - 1) {
                    capitalizedString.append(" ");
                }
            }
        }
        return capitalizedString.toString();
    }
}
