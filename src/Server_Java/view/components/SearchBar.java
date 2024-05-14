package Server_Java.view.components;

import App.User;
import Server_Java.ApplicationServant;
import Server_Java.controller.AdminController;
import Server_Java.dataBase.Database;
import Server_Java.utilities.AdminViews;
import Server_Java.view.AdminMainFrame;
import Server_Java.view.panels.UsersPanel;
import shared.utilities.FontFactory;
import shared.viewComponents.FieldInput;
import shared.viewComponents.IconButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

public class SearchBar extends JPanel {
    private FieldInput searchField;
    private User[] users;
    private UsersList usersList;
    private AdminController adminController;
    private UsersPanel usersPanel;
    public SearchBar(AdminController adminController){
        this.adminController = adminController;
        setBackground(Color.white);
        setLayout(new BorderLayout(0, 50));

        users = Database.getPlayers();

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(AdminMainFrame.WIDTH, 50));
        centerPanel.setBackground(Color.WHITE);

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(Color.white);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;

        IconButton back = new IconButton("src/shared/images/back.png", 40, 40);
        buttonsPanel.add(back, constraints);
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 40, 0, 0);

        searchField = new FieldInput("", new Dimension(600, 50), Integer.MAX_VALUE, 0, false);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBackground(Color.WHITE);
        JLabel label = new JLabel();
        label.setFont(FontFactory.newPoppinsBold(14));
        labelPanel.add(label);

        centerPanel.add(buttonsPanel, BorderLayout.WEST);
        centerPanel.add(searchField, BorderLayout.EAST);

        add(labelPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        back.addActionListener(e -> {
            adminController.changeFrame(AdminViews.HOME_PAGE);
        });

        searchField.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {

                if (!Character.isLetterOrDigit(e.getKeyChar())) {
                    if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) return;
                }

                if (e.isAltDown() || e.isShiftDown() || e.isControlDown()) {
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (searchField.getInput() != null && searchField.getInput().isEmpty()) {
                        searchField.removeError();
                    }
                }

                if (searchField.getInput() != null) {
                    searchField.removeError();
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchTerm = searchField.getInput();
                    if (!searchTerm.isEmpty()) {
                        User[] users = Database.getPlayers();

                        User[] filteredUsers = Arrays.stream(users)
                                .filter(user -> user.firstName.contains(searchTerm) ||
                                        user.lastName.contains(searchTerm) ||
                                        user.userName.contains(searchTerm))
                                .toArray(User[]::new);

                        setUserList(filteredUsers);
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }
    private void searchUsers(String searchTerm) {
        int count = 0;
        for (User user : users) {
            if (userMatchesSearch(user, searchTerm)) {
                System.out.println("Matching User: " + user);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No matching users found for search term: " + searchTerm);
        }
    }

    private boolean userMatchesSearch(User user, String searchTerm) {
        return user.userID.contains(searchTerm) ||
                user.firstName.contains(searchTerm) ||
                user.lastName.contains(searchTerm) ||
                user.userName.contains(searchTerm);
    }
    public void setUserList(User[] users) {
        usersPanel.remove(1);
        usersList = new UsersList(users, adminController);
        usersPanel.add(usersList);
        revalidate();
        repaint();
    }
}
