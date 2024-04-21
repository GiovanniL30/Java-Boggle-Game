package Client_Java.view.components;

import javax.swing.*;
import java.awt.*;

public class PlayerNameBlock extends JPanel {
    private String name;
    private int score;
    private JLabel label;

    public PlayerNameBlock(String name, int score) {
        this.name = name;
        this.score = score;

        setBackground(Color.white);
        label = new JLabel(name + " (" + score + ")");
        add(label);
    }

    public void updateScore(int additionalScore) {
        score += additionalScore;
        label.setText(name + " (" + score + ")");
        revalidate();
        repaint();
    }

}
