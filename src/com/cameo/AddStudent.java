package com.cameo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Cameo on 12/5/2015.
 *
 * This form gathers student user entered information to create a user account.
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
    private JTextField usernameTextField;
    private JPasswordField passwordField1;


    public AddStudent(){
        super("Create an Account");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension (600, 450));

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
                String sUsername = usernameTextField.getText();
                String sPassword = passwordField1.getText();

                //Input validation
                //TODO Better input validation

                if (sFirstName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a first name.");
                } else if (sLastName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a last name.");
                } else if (sEmail.isEmpty() || !sEmail.contains("@") || !sEmail.contains(".")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid email address");
                } else if (sStreetAdd.isEmpty() || sStreetAdd.length() < 6) {
                    JOptionPane.showMessageDialog(null, "Please review the street address you have entered and resubmit the form.");
                } else if (sCity.isEmpty() || sCity.length() < 3) {
                    JOptionPane.showMessageDialog(null, "Please review the city you have entered and resubmit the form.");
                } else if (sState.length() != 2) {
                    JOptionPane.showMessageDialog(null, "You have not chosen a state. Please do so now and resubmit the form.");
                } else if (sPhoneNumber.isEmpty() || sPhoneNumber.length() < 10) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid phone number.");
                } else if (sZip.length() < 5) {
                    JOptionPane.showMessageDialog(null, "You have not entered a valid zip code. Please try again.");
                }
                //TODO check to see if username already exists!
                else if (sUsername.length() < 8) {
                    JOptionPane.showMessageDialog(null, "Usernames must be at lease 8 characters long. Please try again.");
                }
                //TODO require passwords to contain an uppercase and a number
                else if (sPassword.length() < 8) {
                    JOptionPane.showMessageDialog(null, "Passwords must be at least 8 characters long. Please try again.");
                } else {
                    //If all the above validation passes, add the information into the Student Table in the database
                    Student newStudent = new Student(sFirstName, sLastName, sEmail, sStreetAdd, sCity, sState, sZip, sPhoneNumber, sUsername, sPassword);

                    DatabaseManager.createStudentTable();
                    DatabaseManager.saveNewStudent(newStudent);
                    DatabaseManager.shutdown();
                }
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
