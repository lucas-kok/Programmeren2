package com.codecademy.informationhandling.Student;

import java.time.LocalDate;

public class Student {

    private String Email;
    private String Name;
    private LocalDate Birthday;
    private String gender;
    private String Address;
    private String City;
    private String Country;

    //Constructor
    public Student(String email, String name, LocalDate birthday, String gender, String address, String city, String country) {
        this.Email = email;
        this.Name = name;
        this.Birthday = birthday;
        this.gender = gender;
        this.Address = address;
        this.City = city;
        this.Country = country;
    }

    //

    //Getters and Setters
    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public LocalDate getBirthday() {
        return Birthday;
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
}
