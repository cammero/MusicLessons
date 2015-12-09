package com.cameo;

/**
 * Created by Cameo on 12/5/2015.
 */
public class LessonsOffered {
    int courseNumber;
    String name;
    String instrument;
    String level;
    double cost;
    //TODO figure out how to store times
    String time;
    String dayOfWeek;
    String place;
    int instructorID;

    //TODO (same as student) does constructor need PK?
    LessonsOffered(String name, String instrument, String level, double cost){
        this.name = name;
        this.instrument = instrument;
        this.level = level;
        this.cost = cost;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getInstrument() { return instrument; }

    public void setInstrument(String instrument) { this.instrument = instrument; }

    public String getLevel() { return level; }

    public void setLevel(String level) { this.level = level; }

    public double getCost() { return cost; }

    public void setCost(double cost) { this.cost = cost; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place; }

    public String getDayOfWeek() { return dayOfWeek; }

    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public int getInstructorID() { return instructorID; }

    public void setInstructorID(int instructorID) { this.instructorID = instructorID; }
}
