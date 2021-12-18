package nl.pekict.project;

public class Student {

    private String name;
    private String gender;
    private String email;
    private String adress;
    private String city;
    private String country;
    private String birthday;

    public Student(String name, String gender, String email, String adress, String city, String country, String birthday) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public String getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return email + ", " + name + ", " + birthday + ", " + gender + ", " + adress + ", " + city + ", " + country;
    }

}
