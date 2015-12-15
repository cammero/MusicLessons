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

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO login code validation
                //TODO add class to student's classlist OR go back to register screen?
            }
        });
    }
}
