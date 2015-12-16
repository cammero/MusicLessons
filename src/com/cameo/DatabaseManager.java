package com.cameo;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.StringBufferInputStream;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Cameo on 12/5/2015.
 */
public class DatabaseManager {

    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "musiclessons";
    private static final String USER = "cameo";
    private static final String PASS = "chicagobears";



    static Connection conn = null;
    //Use this statement whenever you want to connect to DB?
    static Statement statement = null;
    static Statement createStudentDemoTableStatement = null;

    //TODO new resultset every time I need one
    static ResultSet rs = null;
    static ResultSet findInst = null;

    //Student Demographics table
    public final static String STUDENT_TABLE_NAME = "student_demo";
    public final static String PK_COLUMN = "student_id";
    public final static String S_FNAME = "student_first_name";
    public final static String S_LNAME = "student_last_name";
    public final static String S_EMAIL = "student_email";
    public final static String S_ADDRESS = "student_street_address";
    public final static String S_CITY = "student_city";
    public final static String S_STATE = "student_state";
    public final static String S_ZIP = "student_zip";
    public final static String S_PHONE_NUMBER = "student_phone_number";
    public final static String AMOUNT_PAID = "amount_paid";
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";

    public DatabaseManager(){

        System.out.println("Went straight to set up");
        DatabaseManager.setup();
    }

    //setup() method taken from Clara's MovieRatings program
    public static boolean setup() {
        try {
            //load driver class
            try {
                String Driver = "com.mysql.jdbc.Driver";
                Class.forName(Driver);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Database drivers not found. Quitting program.");
                return false;
            }

            //conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
            conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASS);

            String createDatabase = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(createDatabase);

            System.out.println("Got connected");

            String useDatabase = "USE " + DB_NAME;
            statement.execute(useDatabase);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void createStudentTable () {
        //create student demographics table
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS student_demo (" + PK_COLUMN + " int NOT NULL AUTO_INCREMENT, "
                    + S_FNAME + " varchar(30), " + S_LNAME + " varchar(50), " + S_EMAIL + " varchar(50), " + S_ADDRESS
                    + " varchar(50), " + S_CITY + " varchar(30), " + S_STATE + " char(2), " + S_ZIP + " varchar(10), "
                    + S_PHONE_NUMBER + " varchar(12), " + AMOUNT_PAID + " double, " + USERNAME + " varchar(30), " +
                    PASSWORD + " varchar(30), PRIMARY KEY(" + PK_COLUMN + "))";

            statement.executeUpdate(createTableSQL);
            System.out.println("Created student_demo table if it didn't already exist");
        } catch (SQLException sqle){
            System.out.println(sqle.toString());
        }
    }

    public static boolean studentDemoTableExists() throws SQLException {
        //from Clara's movieDatabase program
        String checkTablePresentQuery = "SHOW TABLES LIKE " + STUDENT_TABLE_NAME;//Can query the database schema
        ResultSet tablesRS = statement.executeQuery(checkTablePresentQuery);
        if (tablesRS.next()) {    //If ResultSet has a next row, it has at least one row... that must be our table
            return true;
        }
        return false;
    }

    public static ResultSet createInstrumentComboBox() {
        try {
            String instrumentComboBoxQuery = "select DISTINCT instrument from musiclessons.instructor_instrument;";
            ResultSet rs = statement.executeQuery(instrumentComboBoxQuery);
            return rs;
        } catch (SQLException sqle) {
            System.out.println("Error getting instruments");
            System.out.println(sqle.toString());
            //TODO When do we want to print out stack trace?
            sqle.printStackTrace();
            //TODO what should this return?
            return null;
        }
    }

    public static ArrayList<String> lessonsWithinACertainRadius(String instrument, String[] zipCodeSearchArray){

        try {
            ArrayList<String> instructorRSArray = new ArrayList<String>();
            //String zip = "55426";
            for (String zip : zipCodeSearchArray) {

                String lessonsWithinRadius = "select firstName, lastName, city, state, hourlyRate from instructor, instructor_instrument " +
                        "where instructor.instructorID = instructor_instrument.instructorID and instrument ='" + instrument + "' and zip = " + zip;

                findInst = statement.executeQuery(lessonsWithinRadius);

                while (findInst.next()) {
                String firstName = findInst.getString("firstName");
                String lastName = findInst.getString("lastName");
                String city = findInst.getString("city");
                String state = findInst.getString("state");
                Double rate = findInst.getDouble("hourlyRate");
                String lineForJList = firstName + " " + lastName + " " + city + "," + state + " $" + rate + " per hour";
                    System.out.println(lineForJList);
                    instructorRSArray.add(lineForJList);
                }
            }
            System.out.println(instructorRSArray.size());
            return instructorRSArray;
        } catch (SQLException sqle){
            System.out.println("Error obtaining music instructors in the vicinity you requested");
            return null;
        }
    }

    public static void saveNewStudent(Student newStudent){
        try {
            //prepared statement help from Week 12 slides
            //statement adds student information to student_demo table in database
            String addDataToStudentTable = "INSERT INTO student_demo (student_first_name, student_last_name, student_email, "
                    + "student_street_address, student_city, student_state, student_zip, student_phone_number, username, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            System.out.println(addDataToStudentTable);
            PreparedStatement psInsert = conn.prepareStatement(addDataToStudentTable);
            psInsert.setString(1, newStudent.getFirstName());
            psInsert.setString(2, newStudent.getLastName());
            psInsert.setString(3, newStudent.getEmail());
            psInsert.setString(4, newStudent.getStreetAddress());
            psInsert.setString(5, newStudent.getCity());
            psInsert.setString(6, newStudent.getState());
            psInsert.setString(7, newStudent.getZipCode());
            psInsert.setString(8, newStudent.getPhoneNumber());
            psInsert.setString(9, newStudent.getUsername());
            psInsert.setString(10, newStudent.getPassword());
            System.out.println(psInsert);
            psInsert.executeUpdate();

            System.out.println("You just created a student");
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }

    //from clara's moviedatabase program
    //close ResultSet, statement and connection, in that order
    public static void shutdown(){
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Result set closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (statement != null) {
                statement.close();
                System.out.println("Statement closed");
            }
        } catch (SQLException se){
            //Closing the connection could throw an exception too
            se.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database connection closed");
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
