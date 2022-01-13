package com.codecademy.informationhandling.validators;

import com.codecademy.informationhandling.InformationFormatter;
import com.codecademy.informationhandling.certificate.Certificate;

import java.util.Map;

public class CertificateInformationValidator {

    public CertificateInformationValidator() {

    }

    public String validateEditedCertificate(int score) {
        if (score >= 1 && score <= 10) {
            return "";
        } else {
            return "Grade must be between 1 and 10!";
        }
    }

}
