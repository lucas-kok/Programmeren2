package com.codecademy.informationhandling.student;

public class Student {
    private String email;
    private String name;
    private final String birthday;
    private final String gender;
    private String address;
    private String city;
    private String country;
    private String postalCode;

    public Student(String email, String name, String birthday, String gender, String address, String city, String country, String postalCode) {
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        String[] birthdayPieces = birthday.split("-");
        return birthdayPieces[2] + "/" + birthdayPieces[1] + "/" + birthdayPieces[0];
    }

    public String[] getBirthdayPieces() {
        return getBirthday().split("/");
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Email='" + email + '\'' +
                ", Name='" + name + '\'' +
                ", Birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", Address='" + address + '\'' +
                ", City='" + city + '\'' +
                ", Country='" + country + '\'' +
                ", PostalCode='" + postalCode + '\'' +
                '}';
    }
}
