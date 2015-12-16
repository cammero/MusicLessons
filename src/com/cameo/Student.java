package com.cameo;

import java.util.ArrayList;

/**
 * Created by Cameo on 12/5/2015.
 */
public class Student {

    private int studentID;
    private String firstName;
    private String lastName;
    private String email;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private double totalPaidForLessons = 0;
    private String username;
    private String password;
    private ArrayList<String> lessonsSchedule;


    public Student(String firstName, String lastName, String email, String streetAddress, String city, String state, String zipCode, String phoneNumber, String username, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public double addToTotalPaidForLessons (double costOfCourse) {
        totalPaidForLessons += totalPaidForLessons + costOfCourse;
        return totalPaidForLessons;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public double getTotalPaidForLessons() { return totalPaidForLessons; }

    public int getStudentID() {return studentID;}
    public void setStudentID(int studentID) {this.studentID = studentID;}

    public ArrayList<String> getLessonsSchedule() {return lessonsSchedule;}
    public void setLessonsSchedule(ArrayList<String> lessonsSchedule) {this.lessonsSchedule = lessonsSchedule;}

    public void setTotalPaidForLessons(double totalPaidForLessons) {
        this.totalPaidForLessons = totalPaidForLessons;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public static void register(String selectedValue) {
        //TODO call query to add string to table Registration (contains studentID and string)
    }
}
