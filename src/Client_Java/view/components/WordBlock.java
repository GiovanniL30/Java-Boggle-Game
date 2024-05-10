package Client_Java.view.components;

import Client_Java.utilities.FontFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WordBlock extends JPanel {
    private String name;
    private int score;
    private JLabel label;

    public WordBlock(String name, int score) {
        this.name = name;
        this.score = score;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.white);

        label = new JLabel(name + " (" + score + ")");
        label.setFont(FontFactory.newPoppinsBold(20));
        label.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(label);
    }

    public String getWord() {
        return name;
    }
}
