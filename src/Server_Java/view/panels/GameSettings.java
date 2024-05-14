package Server_Java.view.panels;

import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FilledButton;
import javax.swing.*;
import java.awt.*;

public class GameSettings extends JPanel {
    private FilledButton cancelButton = new FilledButton("Cancel", new Dimension(300, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.WHITE);
    private FilledButton updateButton = new FilledButton("Update", new Dimension(300, 50), FontFactory.newPoppinsDefault(14), ColorFactory.blue(), Color.WHITE);
    private JLabel waitingTime = new JLabel("Waiting Time:", SwingConstants.CENTER);
    private JLabel gameTime = new JLabel("Game Time:", SwingConstants.CENTER);
    private JComboBox<String> waitingTimeOptions = new JComboBox<>();
    private JComboBox<String> gameTimeOptions = new JComboBox<>();

    public GameSettings() {
        setBackground(ColorFactory.beige());
        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridy = 0;
        constraints.gridx = 0;
        centerPanel.setBackground(ColorFactory.beige());
        waitingTime.setFont(FontFactory.newPoppinsBold(20));
        centerPanel.add(waitingTime, constraints);

        constraints.gridy = 0;
        constraints.gridx = 1;
        String[] wtOptions = {"30", "45", "60", "90"}; // Example options
        waitingTimeOptions = new JComboBox<>(wtOptions);
        waitingTimeOptions.setPreferredSize(new Dimension(200,30));
        centerPanel.add(waitingTimeOptions, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        gameTime.setFont(FontFactory.newPoppinsBold(20));
        centerPanel.add(gameTime, constraints);

        constraints.gridy = 1;
        constraints.gridx = 1;
        String[] gtOptions = {"30", "45", "60", "90"}; // Example options
        gameTimeOptions = new JComboBox<>(gtOptions);
        gameTimeOptions.setPreferredSize(new Dimension(200,30));
        centerPanel.add(gameTimeOptions, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        centerPanel.setBackground(ColorFactory.beige());
        cancelButton.setBackground(ColorFactory.whitishGrey());
        cancelButton.setForeground(Color.BLACK);
        centerPanel.add(cancelButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        centerPanel.add(updateButton, constraints);
        
        add(centerPanel, BorderLayout.CENTER);
    }
}
