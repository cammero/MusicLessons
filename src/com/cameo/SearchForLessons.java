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

    public SearchForLessons () {
        super("Search for music lessons");
        setContentPane(rootPanel2);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(600, 450));

        ResultSet rs = DatabaseManager.createInstrumentComboBox();
        //System.out.println(rs.toString());
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

        searchForLessonsNearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            zip = searchZipTextField.getText();
            milesRadius = radiusTextField.getText();
            //String whichInstrument = (String) instrumentComboBox.getSelectedItem();
            instrumentSelected = instrumentComboBox.getSelectedItem().toString();

            try {
                if (zip.length()!=5){
                    System.out.println("Enter a valid five digit zip code");
                }
            } catch (Exception ex){
                ex.toString();
            }

            ZipCodeWorker doTheSearch = new ZipCodeWorker(zip, milesRadius, SearchForLessons.this);
            doTheSearch.execute();

            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO use popup?
                //TODO Query username and password, if match add class to student's schedule
                LogInPopUp dialog = new LogInPopUp();
                Student.register(listOfInstructorsJList.getSelectedValue());
            }
        });
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

        //next 3 lines of code obtained from Clara's LogList program
        instructorListModel = new DefaultListModel<String>();
        listOfInstructorsJList.setModel(instructorListModel);
        listOfInstructorsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


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
