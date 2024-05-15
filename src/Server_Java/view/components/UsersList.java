package Server_Java.view.components;

import App.User;
import Server_Java.controller.AdminController;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class UsersList extends JPanel {

    private AdminController adminController;
    private final JPanel holder = new JPanel();

    public UsersList(User[] users, AdminController adminController) {
        this.adminController = adminController;
        setBackground(Color.white);

        // Set layout for the holder panel
        GridLayout gridLayout = new GridLayout(0, 1);
        gridLayout.setVgap(20);
        holder.setLayout(gridLayout);
        holder.setBackground(Color.white);

        // Update the view with the given list of users
        updateView(users);

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(holder);
        scrollPane.setPreferredSize(new Dimension(840, 310));
        add(scrollPane);
    }

    /**
     * Updates the view with the given list of users.
     */
    public void updateView(User[] users) {
        // Use SwingWorker to update the view in the background
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Clear the holder panel
                SwingUtilities.invokeLater(() -> {
                    holder.removeAll();
                    holder.revalidate();
                    holder.repaint();
                });

                // Add user cards for each user to the holder panel
                for (User user : users) {
                    SwingUtilities.invokeLater(() -> {
                        holder.add(new UsersCard(user, adminController));
                        holder.revalidate();
                        holder.repaint();
                    });
                }
                return null;
            }
        }.execute();
    }
}
