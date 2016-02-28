package com.cameo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Cameo on 12/14/2015.
 */
public class LogIn extends JFrame{
    private JButton logInButton;
    private JTextField passwordTextField;
    private JTextField userNameTextField;
    private JPanel rootPanel3;

    public LogIn () {
        super("Log In to Your Student Account");
        setContentPane(rootPanel3);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(300, 300));

        //This method gets the username and password entered by the user and compares it to the users and corresponding
        //passwords in the data base. It currently does NOT work.
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userNameTextField.getText();
                String password = passwordTextField.getText();
                if (username.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a username.");
                }
                if (password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter a password.");
                }

                //TODO get the authentication to work
                boolean valid = DatabaseManager.authentication(username, password);
                if (!valid){
                    JOptionPane.showMessageDialog(null, "You have not entered a valid username and/or password.");
                }
                else {
                    //TODO add class to student's classlist
                }
            }
        });
    }
}
