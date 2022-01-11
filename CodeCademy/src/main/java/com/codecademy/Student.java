package com.codecademy;

import java.time.LocalDate;

public class Student {
    private String name;
    private String email;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String gender;
    private String birthday;

    public Student(String name, String email, String address, String postalCode, String city, String country, String gender, String birthday) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String Gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthdayAsLocalDate() {
        String[] birthdayPieces = getBirthday().split("-");
        String birthdayString = birthdayPieces[2] + "-" + birthdayPieces[1] + "-" + birthdayPieces[0];

        LocalDate birthDay = LocalDate.parse(birthdayString);
        return birthDay;
    }

    public String toString() {
        return name + ", " + email + ", " + address + ", " + postalCode + ", " + city + ", " + country + ", " + gender + ", " + birthday;
    }

}
