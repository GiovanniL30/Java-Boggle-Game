package Client_Java.view.panels;

import App.User;
import shared.utilities.FontFactory;

import javax.swing.*;
import java.awt.*;

public class RoundWinner extends JPanel {

    public RoundWinner(User roundWinner, int score, int round) {
        setLayout(new GridBagLayout());

        JLabel label = new JLabel("Round " + round + " Winner: " + roundWinner.userName + ": " + score);
        label.setFont(FontFactory.newPoppinsBold(20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;
        add(label, gbc);
    }

}
