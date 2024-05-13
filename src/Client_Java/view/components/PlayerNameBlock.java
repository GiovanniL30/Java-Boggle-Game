package Client_Java.view.components;

import shared.utilities.FontFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlayerNameBlock extends JPanel {
    private String name;
    private int score;
    private JLabel label;
    private String playerId;

    public PlayerNameBlock(String name, int score, String playerId, int fontSize, boolean needScore) {
        this.playerId = playerId;
        this.name = name;
        this.score = score;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(new Color(255,246, 238));

        if (needScore){
            label = new JLabel(name + " (" + score + ")");
            label.setFont(FontFactory.newPoppinsBold(fontSize));
            label.setBorder(new EmptyBorder(10, 0, 10, 0));
            add(label);
        } else {
            label = new JLabel(name);
            label.setFont(FontFactory.newPoppinsBold(fontSize));
            label.setBorder(new EmptyBorder(10, 0, 10, 0));
            add(label);
        }
    }

    public void updateScore(int score) {
        this.score = score;
        label.setText(name + " (" + this.score + ")");
        revalidate();
        repaint();
    }

    public String getPlayerId() {
        return playerId;
    }
}
