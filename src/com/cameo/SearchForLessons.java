package com.cameo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Cameo on 12/7/2015.
 */
public class SearchForLessons extends JFrame{
    private JButton searchForLessonsButton;
    private JTextField searchZipTextField;
    private JPanel rootPanel2;
    private JComboBox instrumentComboBox;
    private JTable lessonsOfferedTable;
    private JScrollPane lessonsOfferedScrollPane;

public SearchForLessons (){
        super("Search for music lessons");
        setContentPane(rootPanel2);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(500, 300));
    }
}
