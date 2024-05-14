package Server_Java.view.components;

import App.User;
import Server_Java.controller.AdminController;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class UsersList extends JPanel {

    private AdminController adminController;
    private final JPanel holder = new JPanel();

    public UsersList(LinkedList<User> users, AdminController adminController) {
        this.adminController = adminController;
        setBackground(Color.white);

        // Set layout for the holder panel
        GridLayout gridLayout = new GridLayout(0, 1);
        gridLayout.setVgap(20);
        holder.setLayout(gridLayout);
        holder.setBackground(Color.white);

        // Update the view with the given list of students
        updateView(users);

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(holder);
        scrollPane.setPreferredSize(new Dimension(920, 550));
        add(scrollPane);
    } // end of constructor

    /**
     * Updates the view with the given list of students.
     */
    public void updateView(LinkedList<User> users) {
        // Use SwingWorker to update the view in the background
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {
                // Clear the holder panel if the list is empty
                if (users.isEmpty()) {
                    holder.revalidate();
                    holder.repaint();
                }

                // Add account cards for each student to the holder panel
                for(User user: users) {
                    holder.add(new UsersCard(user, adminController));
                    holder.revalidate();
                    holder.repaint();
                }
                return null;
            }
        }.execute();
    } // end of updateView method
} // end of ManageAccountList class
