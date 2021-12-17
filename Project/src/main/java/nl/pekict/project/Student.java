package nl.pekict.project;

import java.util.Date;

public class Student {

    private String name;
    private String gender;
    private String email;
    private String adress;
    private String city;
    private String country;
    private Date birthday;

    public Student(String name, String gender, String email, String adress, String city, String country, Date birthday) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.adress = adress;
        this.city = city;
        this.country = country;
        this.birthday = birthday;
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return adress;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return email + ", " + name + ", " + birthday + ", " + gender + ", " + adress + ", " + city + ", " + country;
    }

}
