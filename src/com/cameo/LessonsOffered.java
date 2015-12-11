package com.cameo;

/**
 * Created by Cameo on 12/5/2015.
 */
public class LessonsOffered {
    int courseNumber;
    String name;
    String instrument;
    double cost;
    //TODO figure out how to store times
    String timeOfDay;
    String dayOfWeek;
    String place;
    int instructorID;

    //TODO (same as student) does constructor need PK?
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

    public String getTime() { return timeOfDay; }

    public void setTime(String time) { this.timeOfDay = timeOfDay; }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place; }

    public String getDayOfWeek() { return dayOfWeek; }

    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public int getInstructorID() { return instructorID; }

    public void setInstructorID(int instructorID) { this.instructorID = instructorID; }
}
