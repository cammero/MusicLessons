package com.cameo;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Cameo on 12/7/2015.
 */
public class SearchForLessons extends JFrame{
    private JButton searchForLessonsNearButton;
    private JTextField searchZipTextField;
    private JPanel rootPanel2;
    public JComboBox instrumentComboBox;
    private JTextField radiusTextField;
    private JList list1;

    public SearchForLessons () {
        super("Search for music lessons");
        setContentPane(rootPanel2);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(500, 300));


        ResultSet rs = DatabaseManager.createInstrumentComboBox();
        System.out.println(rs.toString());
        try {
            while (rs.next()) {
                //saw this on youtube https://www.youtube.com/watch?v=lrvm5B1PcO0
                String instrument = rs.getString("instrument");
                instrumentComboBox.addItem(rs.getString(instrument));
                //instrumentComboBox.addItem(rs.getString(1));
            }
        }
        catch (SQLException sqle){
            //JOptionPane.showMessageDialog(sqle);
            System.out.println("Error getting types of lessons offered");
        }

        searchForLessonsNearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String whichInstrument = (String) instrumentComboBox.getSelectedItem();
                String zip = searchZipTextField.getText();
                String radius = radiusTextField.getText();
                //TODO write zip code search API
            }
        });
    }
}
