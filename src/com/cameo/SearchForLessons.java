package com.cameo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Cameo on 12/7/2015.
 *
 * This form allows a user to select an instrument from a dropdown, enter their zip code and the radius in miles that
 * they are willing to travel to take music lessons. It returns all instructors within that range.
 */

public class SearchForLessons extends JFrame implements WindowListener, ZipCodesFetchedListener {
    private JButton searchForLessonsNearButton;
    private JTextField searchZipTextField;
    private JPanel rootPanel2;
    public JComboBox instrumentComboBox;
    private JTextField radiusTextField;
    private JList<String> listOfInstructorsJList;
    private JButton registerButton;
    private JButton createNewAccountButton;

    String instrumentSelected;
    String zip;
    String milesRadius;

    DefaultListModel<String> instructorListModel;

    //First GUI screen
    public SearchForLessons () {
        super("Search for music lessons");
        setContentPane(rootPanel2);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(600, 450));

        //Create dropdown of all types of lessons offered. Searched through database each time the program is run, so
        // the list will be accurate.
        ResultSet rs = DatabaseManager.createInstrumentComboBox();
        try {
            while (rs.next()) {
                //saw this on youtube https://www.youtube.com/watch?v=lrvm5B1PcO0
                String instrument = rs.getString("instrument");
                instrumentComboBox.addItem(instrument);
            }
        }
        catch (SQLException sqle){
            System.out.println(sqle.toString());
            System.out.println("Error getting types of lessons offered");
        }

        //This button gathers the parameters necessary to find any zip codes of instructors that fall within the radius
        //specified by the user.
        searchForLessonsNearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            zip = searchZipTextField.getText();
            milesRadius = radiusTextField.getText();
            instrumentSelected = instrumentComboBox.getSelectedItem().toString();

            try {
                if (zip.isEmpty() || zip.length()!= 5){
                    JOptionPane.showMessageDialog(null, "You have not entered a valid five digit zip code. Please try again.");
                }
                else if (milesRadius.isEmpty() || Integer.parseInt(milesRadius) < 0){
                    JOptionPane.showMessageDialog(null, "You have not entered a valid number of miles. Please try again.");
                }
                else {
                    //If the zip code and mile parameters are valid, the search for the zip codes that are within that radius
                    //will be executed by the API call.
                    ZipCodeWorker doTheSearch = new ZipCodeWorker(zip, milesRadius, SearchForLessons.this);
                    doTheSearch.execute();
                }
            } catch (Exception ex){
                ex.toString();
            }

            }
        });

        //If student already has an account, they click this. LogIn information is required. (Not currently functioning.)
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO Query username and password, if match add class to student's schedule
                //Use the popup?
                //LogInPopUp dialog = new LogInPopUp();
                LogIn logIn = new LogIn(); //Doesn't currently work

                //Add selected course to student lessonsScheduled ArrayList (Not currently functioning.)
                Student.register(listOfInstructorsJList.getSelectedValue());
            }
        });

        //If the user does not have an account, this button brings up a form for them to create one.
        createNewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudent addStudentForm = new AddStudent();
                Student.register(listOfInstructorsJList.getSelectedValue());
            }
        });
    }

    //Interface method
    @Override
    public void zipCodesFetched(String[] zipcodes) {

        System.out.println("In the GUI, and have been notified that zip codes are available");
        for (String zipCode : ZipCodeListBean.zipCodeList){
            System.out.println(zipCode);
        }

        ArrayList<String> lessonChoices = DatabaseManager.lessonsWithinACertainRadius(instrumentSelected, ZipCodeListBean.zipCodeList);

        //next 3 lines of code mirrored from Clara's LogList program
        instructorListModel = new DefaultListModel<String>();
        listOfInstructorsJList.setModel(instructorListModel);
        listOfInstructorsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Adds each instructor to the list
        for (String instructor : lessonChoices) {
            instructorListModel.addElement(instructor);
        }
        if (instructorListModel.isEmpty()){
            instructorListModel.addElement("There are no instructors teaching " + instrumentSelected + " in the requested area.");
        }
    }


    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        DatabaseManager.shutdown();}
    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}


}
