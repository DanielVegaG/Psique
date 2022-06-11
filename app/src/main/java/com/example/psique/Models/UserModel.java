package com.example.psique.Models;

/**
 * Esta clase contiene los datos de los usuarios
 */
public class UserModel {
    //atributos
    private String uid, firstName, lastName, phone, bio;
    private double birthDate;
    private boolean professional;

    //constructor
    public UserModel() {

    }

    //getters y setters

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(double birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isProfessional() {
        return professional;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }
}
