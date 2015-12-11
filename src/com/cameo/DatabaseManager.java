package com.cameo;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;

/**
 * Created by Cameo on 12/5/2015.
 */
public class DatabaseManager {

    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "musicLessons";
    private static final String USER = "cameo";
    private static final String PASS = "chicagobears";

    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;

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

    public DatabaseManager(){
        //create the database if it doesn't exist
        System.out.println("Go straight to set up");
        //TODO Ugh!
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

            conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);

            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Got connected");
            if (!DatabaseManager.studentDemoTableExists()) {

                //TODO (possibly fixed) Is the "PRIMARY KEY (student_id) at the end of this statement correct?
                String createTableSQL = "CREATE TABLE IF NOT EXISTS student_demo (" + PK_COLUMN + " int NOT NULL AUTO_INCREMENT, "
                        + S_FNAME + " varchar(30), " + S_LNAME + " varchar(50), " + S_EMAIL + " varchar(50), " + S_ADDRESS
                        + " varchar(50), " + S_CITY +  " varchar(30), " + S_STATE + " char(2), " + S_ZIP + " varchar(10), "
                        + S_PHONE_NUMBER + " varchar(12), " + AMOUNT_PAID + " double, PRIMARY KEY(" + PK_COLUMN + "))";
                System.out.println(createTableSQL);
                statement.executeUpdate(createTableSQL);
                System.out.println("Created student_demo table");
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean studentDemoTableExists() throws SQLException {
        //from Clara's movieDatabase program
        String checkTablePresentQuery = "SHOW TABLES LIKE " + STUDENT_TABLE_NAME;//Can query the database schema
        ResultSet tablesRS = statement.executeQuery(checkTablePresentQuery);
        if (tablesRS.next()) {    //If ResultSet has a next row, it has at least one row... that must be our table
            return true;
        }
        return false;

//        try{
//            System.out.println("Checking to see if the table is present");
//            ResultSet tableRS = statement.executeQuery(checkTablePresentQuery);
//            if (tableRS.next()) {
//                return true;
//            }
//            return false;
//        } catch (SQLException se) {
//            se.printStackTrace();
//        }
//        return false;
    }

    public void saveNew(Student newStudent){
        try {
            //prepared statement help from Week 12 slides
            String addDataToStudentTable = "INSERT INTO student_demo (student_first_name, student_last_name, student_email, "
                    + "student_street_address, student_city, student_state, student_zip, student_phone_number) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
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
            System.out.println(psInsert);
            psInsert.executeUpdate();

//        String addDataToStudentTable = "INSERT INTO " + STUDENT_TABLE_NAME + " (" + S_FNAME + ", " + S_LNAME + ", " + S_EMAIL
//            + ", " + S_ADDRESS + ", " + S_CITY + ", " + S_STATE + ", " + S_ZIP + ", " + S_PHONE_NUMBER + ") VALUES (" + newStudent.getFirstName() + ", " +
//            newStudent.getLastName() + ", " + newStudent.getEmail() + ", " + newStudent.getStreetAddress() + ", " +
//            newStudent.getCity() + ", " + newStudent.getState() + ", " + newStudent.getZipCode() + ", " + newStudent.getPhoneNumber() + ")";
//            System.out.println(addDataToStudentTable);
//        statement.executeUpdate(addDataToStudentTable);

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
