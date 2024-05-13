package Client_Java.view.panels;

import App.User;
import Client_Java.controller.ClientController;
import Client_Java.utilities.ClientViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FilledButton;

import javax.swing.*;
import java.awt.*;


public class GameSummary extends JPanel {

    public GameSummary(User winner, int score, ClientController clientController) {

        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.white);

        JLabel label = new JLabel("Winner: " + winner.userName + ": " + score);
        label.setFont(FontFactory.newPoppinsBold(20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        FilledButton button = new FilledButton("Return to Home Page", new Dimension(100, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.white);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(label);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(button);
        contentPanel.add(Box.createVerticalGlue());

        button.addActionListener(e -> clientController.changeFrame(ClientViews.HOME_PAGE));


        add(contentPanel, BorderLayout.CENTER);
    }
}
