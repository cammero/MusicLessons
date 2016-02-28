package com.cameo;

/**
 * Created by Cameo on 12/5/2015.
 */
public class Instructor {

    private int instructorID;
    private String firstName;
    private String lastName;
    private String email;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private double earnings = 0;
    private String instrument;
    private double hourlyRate;

    public Instructor(String firstName, String lastName, String email, String streetAddress, String city, String state,
                      String zipCode, String phoneNumber, String instrument, double hourlyRate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.instrument = instrument;
        this.hourlyRate = hourlyRate;
    }

    //Earnings to be calculated in version 2
    public double addToEarnings (double costOfClass){
        //instructor gets paid 60% of what the lesson costs
        earnings += costOfClass*.6;
        return earnings;
    }

    //The JList will use this to create a string to display in the GUI
    public String toString() {
            return (firstName + " teaches " + instrument + " lessons for $" + hourlyRate + " an hour");
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

    public double getEarnings() { return earnings; }

    public int getInstructorID() { return instructorID; }

    public String getInstrument() { return instrument; }
    public void setInstrument(String instrument) { this.instrument = instrument; }

    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(int hourlyRate) { this.hourlyRate = hourlyRate; }
}

