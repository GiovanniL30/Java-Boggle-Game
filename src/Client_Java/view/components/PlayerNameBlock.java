package Client_Java.view.components;

import Client_Java.utilities.FontFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlayerNameBlock extends JPanel {
    private String name;
    private int score;
    private JLabel label;

    public PlayerNameBlock(String name, int score) {
        this.name = name;
        this.score = score;
        setSize(new Dimension(250, 50));
        setLayout(new FlowLayout(FlowLayout.CENTER));


        label = new JLabel(name + " (" + score + ")");
        label.setFont(FontFactory.newPoppinsBold(20));
        label.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(label);
    }

    public void updateScore(int additionalScore) {
        score += additionalScore;
        label.setText(name + " (" + score + ")");
        revalidate();
        repaint();
    }

}
