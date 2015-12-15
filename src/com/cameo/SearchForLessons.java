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

/**
 * Created by Cameo on 12/7/2015.
 */
public class SearchForLessons extends JFrame implements WindowListener {
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
        //DatabaseManager.setup();

        ResultSet rs = DatabaseManager.createInstrumentComboBox();
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
        }

        searchForLessonsNearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            String whichInstrument = (String) instrumentComboBox.getSelectedItem();

            String zip = searchZipTextField.getText();
            try {
                if (zip.length()!=5){
                    System.out.println("Enter a valid five digit zip code");
                }
            } catch (Exception ex){
                ex.toString();
            }
            String milesRadius = radiusTextField.getText();
            //TODO data validation-only numbers

            ZipCodeWorker doTheSearch = new ZipCodeWorker(zip, milesRadius);
            doTheSearch.execute();

            DatabaseManager databaseManager = new DatabaseManager();
//            for (String zipCode : ZipCodeListBean.zipCodeList) {
//                System.out.println(zipCode);
//            }

            //TODO What the heck am I doing?!
                //trying to populate list/table/etc with result list of lessonsWithinACertainRadius
                // public final InstructorsToChooseFromModel instructorsToChooseFromModel;
//            ResultSet rs2 = DatabaseManager.lessonsWithinACertainRadius();
//                try {
//                    if (instructorsToChooseFromModel == null){
//                    }
//                }
//                catch (SQLException sqle){
//                    System.out.println(sqle.toString());;
//                    System.out.println("Error!!");
//                }


            }
        });
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
