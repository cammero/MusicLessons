package com.cameo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Cameo on 12/5/2015.
 */
public class AddStudent extends JFrame{
    private JTextField studentFirstNameTextField;
    private JTextField studentLastNameTextField;
    private JTextField studentEmailTextField;
    private JTextField studentStreetAddressTextField;
    private JComboBox state;
    private JTextField studentZipCodeTextField;
    private JTextField phoneNumberTextField;
    private JButton submitButton;
    private JPanel rootPanel;
    private JTextField studentCityTextField;
    private JButton quitButton;


    public AddStudent(){
        super("Create an Account");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension (300, 450));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String sFirstName = studentFirstNameTextField.getText();
            String sLastName = studentLastNameTextField.getText();
            String sEmail = studentEmailTextField.getText();
            String sPhoneNumber = phoneNumberTextField.getText();
            String sStreetAdd = studentStreetAddressTextField.getText();
            String sCity = studentCityTextField.getText();
            String sState = state.getSelectedItem().toString();
            String sZip = studentZipCodeTextField.getText();

            Student newStudent = new Student(sFirstName, sLastName, sEmail, sStreetAdd, sCity, sState, sZip, sPhoneNumber);

            DatabaseManager.setup(); //This line is only in here to for testing
            DatabaseManager.createStudentTable();
            DatabaseManager.saveNewStudent(newStudent);
            DatabaseManager.shutdown();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
