package Server_Java.view.panels;

import Server_Java.controller.AdminController;
import Server_Java.utilities.AdminViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FilledButton;
import javax.swing.*;
import java.awt.*;

public class AdminHomePage extends JPanel {
    private FilledButton gameSettings = new FilledButton("Game Settings", new Dimension(300, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.WHITE);
    private FilledButton players = new FilledButton("Players", new Dimension(300, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.WHITE);
    private JLabel title = new JLabel("Boggled Game Settings", SwingConstants.CENTER);

    public AdminHomePage(AdminController adminController) {
        setBackground(ColorFactory.beige());
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridy = 0;
        centerPanel.setBackground(ColorFactory.beige());
        title.setFont(FontFactory.newPoppinsBold(40));
        centerPanel.add(title, constraints);

        constraints.gridy = 1;
        centerPanel.setBackground(ColorFactory.beige());
        gameSettings.setBackground(ColorFactory.whitishGrey());
        gameSettings.setForeground(Color.BLACK);
        centerPanel.add(gameSettings, constraints);

        constraints.gridy = 2;
        centerPanel.add(players, constraints);

        gameSettings.addActionListener(e -> adminController.changeFrame(AdminViews.GAME_SETTINGS));

        players.addActionListener(e -> adminController.changeFrame(AdminViews.PLAYERS));
        add(centerPanel, BorderLayout.CENTER);

    }
}
