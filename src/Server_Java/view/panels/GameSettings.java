package Server_Java.view.panels;
import Server_Java.dataBase.Database;
import Server_Java.utilities.AdminViews;
import Server_Java.controller.AdminController;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FilledButton;
import javax.swing.*;
import java.awt.*;

public class GameSettings extends JPanel {
    private FilledButton cancelButton = new FilledButton("Cancel", new Dimension(300, 50), FontFactory.newPoppinsDefault(14), ColorFactory.whitishGrey(), Color.BLACK);
    private FilledButton updateButton = new FilledButton("Update", new Dimension(300, 50), FontFactory.newPoppinsDefault(14), ColorFactory.mahogany(), Color.WHITE);
    private JLabel waitingTime = new JLabel("Waiting Time:", SwingConstants.CENTER);
    private JLabel gameTime = new JLabel("Game Time:", SwingConstants.CENTER);
    private JComboBox<Integer> waitingTimeOptions;
    private JComboBox<Integer> gameTimeOptions;

    public GameSettings(AdminController adminController) {
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
//        Integer[] wtOptions = {30, 45, 60, 90}; // Example options
        Integer[] time = new Integer[adminController.getTime().length];
        for (int i = 0; i < adminController.getTime().length; i++) {
            time[i] = adminController.getTime()[i];
        }

//        DefaultComboBoxModel<Integer> timeOptions = new DefaultComboBoxModel<>(time);
        waitingTimeOptions = new JComboBox<>(time);
        waitingTimeOptions.setPreferredSize(new Dimension(200,30));
        waitingTimeOptions.setSelectedItem(Database.getWaitingTime());
        centerPanel.add(waitingTimeOptions, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        gameTime.setFont(FontFactory.newPoppinsBold(20));
        centerPanel.add(gameTime, constraints);

        constraints.gridy = 1;
        constraints.gridx = 1;
//        Integer[] gtOptions = {30, 45, 60, 90}; // Example options
        gameTimeOptions = new JComboBox<>(time);
        gameTimeOptions.setPreferredSize(new Dimension(200,30));
        gameTimeOptions.setSelectedItem(Database.getGameTime());
        centerPanel.add(gameTimeOptions, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        centerPanel.setBackground(ColorFactory.beige());
        centerPanel.add(cancelButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        centerPanel.add(updateButton, constraints);

        cancelButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Cancel", JOptionPane.YES_NO_OPTION);
            if (response  == JOptionPane.YES_OPTION) {
                adminController.changeFrame(AdminViews.HOME_PAGE);
            }
        });

        updateButton.addActionListener(e -> {

            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to update?", "Save", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                adminController.updateGameTime((Integer) gameTimeOptions.getSelectedItem());
                adminController.updateWaitingTime((Integer) waitingTimeOptions.getSelectedItem());

                JOptionPane.showMessageDialog(null, "Updated Successfully");
                adminController.changeFrame(AdminViews.HOME_PAGE);
            }


        });
        add(centerPanel, BorderLayout.CENTER);
    }
}
