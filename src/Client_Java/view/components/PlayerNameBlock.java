package Client_Java.view.components;

import Client_Java.utilities.FontFactory;

import javax.swing.*;
import java.awt.*;

public class PlayerNameBlock extends JPanel {
    private String name;
    private int score;
    private JLabel label;

    public PlayerNameBlock(String name, int score) {
        this.name = name;
        this.score = score;
        setSize(new Dimension(250, 50));

        label = new JLabel(name + " (" + score + ")");
        label.setFont(FontFactory.newPoppinsBold(17));
        add(label);
    }

    public void updateScore(int additionalScore) {
        score += additionalScore;
        label.setText(name + " (" + score + ")");
        revalidate();
        repaint();
    }

}
