package Client_Java.view.components;

import Client_Java.utilities.FontFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LetterBlock extends JPanel {
    private String letter;
    private JLabel label;

    public LetterBlock(String letter) {
        this.letter = letter;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        label = new JLabel(letter);
        label.setFont(FontFactory.newPoppinsBold(20));
        label.setBorder(new EmptyBorder(10, 25, 10, 10));
        add(label);
    }
}
