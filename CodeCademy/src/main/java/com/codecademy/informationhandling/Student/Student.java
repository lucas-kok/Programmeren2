package Student;

import java.time.LocalDate;

public class Student {

    private String Email;
    private String Name;
    private LocalDate Birthday;
    private char gender;
    private String Address;
    private String City;
    private String Country;

    //Constructor
    public Student(String email, String name, LocalDate birthday, char gender, String address, String city, String country) {
        Email = email;
        Name = name;
        Birthday = birthday;
        this.gender = gender;
        Address = address;
        City = city;
        Country = country;
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

    public char getGender() {
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

    public void setGender(char gender) {
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
