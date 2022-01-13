package com.codecademy.informationhandling.student;

import java.time.LocalDate;

public class Student {

    private String Email;
    private String Name;
    private LocalDate Birthday;
    private String gender;
    private String Address;
    private String City;
    private String Country;
    private String PostalCode;

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public Student(String email, String name, LocalDate birthday, String gender, String address, String city, String country, String postalCode) {
        Email = email;
        Name = name;
        Birthday = birthday;
        this.gender = gender;
        Address = address;
        City = city;
        Country = country;
        PostalCode = postalCode;
    }

    //Getters and Setters
    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getBirthday() {
        String[] pieces = this.Birthday.toString().split("-");
        String answer = pieces[2] + "/" + pieces[1] + "/" + pieces[0];
        return answer;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setBirthday(LocalDate birthday) {
        Birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String[] getBirthdayPieces() {
        String[] pieces  = new String[3];
        pieces[0] = this.Birthday.toString().split("-")[2];
        pieces[1] = this.Birthday.toString().split("-")[1];
        pieces[2] = this.Birthday.toString().split("-")[0];
        return pieces;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Email='" + Email + '\'' +
                ", Name='" + Name + '\'' +
                ", Birthday=" + Birthday +
                ", gender='" + gender + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", Country='" + Country + '\'' +
                ", PostalCode='" + PostalCode + '\'' +
                '}';
    }
}
