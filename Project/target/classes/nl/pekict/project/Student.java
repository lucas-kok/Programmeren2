/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 *
 * @author renzo
 */
public class Student {

    private String name;
    private String gender;
    private String email;
    private String adress;
    private String city;
    private String country;

    private int birthday;

    //CLASS CONSTRUCTOR
    public Student(String name, String gender, String email, String adress, String city, String country, int birthday) {

    }

    
    //CREATE
    public void createStudent(String name, String gender, String email, String adress, String city, String country, int birthday) {

    }

    //UPDATE
    public void updateStudent(String name, String gender, String email, String adress, String city, String country, int birthday) {

        this.name = name;
        this.gender = gender;
        this.email = email;
        this.adress = adress;
        this.city = city;
        this.country = country;
        this.birthday = birthday;
    }

    // DELETE
    public void deleteStudent() {

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

    public int getBirthday() {
        return birthday;
    }

}
