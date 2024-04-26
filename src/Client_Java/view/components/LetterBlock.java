package Client_Java.view.components;

import Client_Java.utilities.FontFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LetterBlock extends JPanel {
    private String letter;
    private JLabel label;

    public LetterBlock(String letter){
        this.letter = letter;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(new Dimension(20,20));

        label = new JLabel(letter);
        label.setFont(FontFactory.newPoppinsBold(20));
        label.setBorder(new EmptyBorder(110, 10, 10, 10));
        add(label);
    }
}
