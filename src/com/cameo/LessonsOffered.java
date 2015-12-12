package com.cameo;

/**
 * Created by Cameo on 12/5/2015.
 */
public class LessonsOffered {
    int courseNumber;
    String name;
    String instrument;
    double cost;
    int instructorID;

    LessonsOffered(String name, String instrument, double cost){
        this.name = name;
        this.instrument = instrument;
        this.cost = cost;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getInstrument() { return instrument; }

    public void setInstrument(String instrument) { this.instrument = instrument; }

    public double getCost() { return cost; }

    public void setCost(double cost) { this.cost = cost; }

    public int getInstructorID() { return instructorID; }

    public void setInstructorID(int instructorID) { this.instructorID = instructorID; }
}
