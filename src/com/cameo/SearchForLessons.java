package com.cameo;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Cameo on 12/7/2015.
 */
public class SearchForLessons extends JFrame implements WindowListener, ZipCodesFetchedListener {
    private JButton searchForLessonsNearButton;
    private JTextField searchZipTextField;
    private JPanel rootPanel2;
    public JComboBox instrumentComboBox;
    private JTextField radiusTextField;
    private JList listOfPossibleInstructors;

    public SearchForLessons () {
        super("Search for music lessons");
        setContentPane(rootPanel2);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //setSize(new Dimension(450, 250));

        DatabaseManager.setup();

       /* ResultSet rs = DatabaseManager.createInstrumentComboBox();
        System.out.println(rs.toString());
        try {
            while (rs.next()) {
                //saw this on youtube https://www.youtube.com/watch?v=lrvm5B1PcO0
                String instrument = rs.getString("instrument");
                instrumentComboBox.addItem(instrument);
            }
        }
        catch (SQLException sqle){
            System.out.println(sqle.toString());;
            System.out.println("Error getting types of lessons offered");
        }*/

        searchForLessonsNearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            //when this button is clicked

            String zip = searchZipTextField.getText();
            String milesRadius = radiusTextField.getText();

            try {
                if (zip.length()!=5){
                    System.out.println("Enter a valid five digit zip code");
                }
            } catch (Exception ex){
                ex.toString();
            }

            ZipCodeWorker doTheSearch = new ZipCodeWorker(zip, milesRadius, SearchForLessons.this);
            doTheSearch.execute();

            //You might need to move this to the zipCodesFetched method too? This depends on knowing zip codes?
        
            String whichInstrument = (String) instrumentComboBox.getSelectedItem();

//            try {
//                while (ZipCodeWorker.getAllZipCodesReturned().isEmpty()){
//                    wait();}
//            } catch (InterruptedException ie){
//                ie.toString();
//            }

                /*try {
                wait(1);
                for (String zipCode : ZipCodeListBean.zipCodeList){
                    System.out.println(zipCode);
                } } catch (InterruptedException ie) {
                ie.toString();
            }*/


        //TODO populate table with result set of lessonsWithinACertainRadius()

            }
        });
    }


    //Interface method
    @Override
    public void zipCodesFetched(String[] zipcodes) {

        //This is where you should make the database call
        //testing to make sure zipcodes are accessible here...

        System.out.println("In the GUI, and have been notified that zip codes are available");
        for (String zipCode : ZipCodeListBean.zipCodeList){
            System.out.println(zipCode);
        }

        //DatabaseManager.lessonsWithinACertainRadius();
        //I don't have your database set up, so the above call fails - but at this point you
        // have the zipcode info and can use it in your DB calls.

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
