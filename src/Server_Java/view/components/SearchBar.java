package Server_Java.view.components;

import App.User;
import Server_Java.controller.AdminController;
import Server_Java.dataBase.Database;
import Server_Java.utilities.AdminViews;
import Server_Java.view.AdminMainFrame;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FieldInput;
import shared.viewComponents.IconButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class SearchBar extends JPanel {
    private final FieldInput searchField;
    private UsersList usersList;

    public SearchBar(AdminController adminController) {

        setBackground(ColorFactory.beige());
        setLayout(new BorderLayout(0, 5));

        User[] users = adminController.getUsers();

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(AdminMainFrame.WIDTH, 50));
        centerPanel.setBackground(ColorFactory.beige());

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(ColorFactory.beige());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;

        IconButton back = new IconButton("src/shared/images/back.png", 40, 40);
        back.setBackground(ColorFactory.beige());
        buttonsPanel.add(back, constraints);
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 40, 0, 0);

        searchField = new FieldInput("", new Dimension(600, 50), Integer.MAX_VALUE, 0, false);
        searchField.setBackground(ColorFactory.beige());
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBackground(ColorFactory.beige());
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

                String searchTerm = searchField.getInput().toUpperCase();


                User[] filteredUsers = Arrays.stream(users).filter(user -> user.firstName.toUpperCase().contains(searchTerm) || user.lastName.toUpperCase().contains(searchTerm) || user.userName.toUpperCase().contains(searchTerm)).toArray(User[]::new);

                usersList.updateView(filteredUsers);

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public void setUsersList(UsersList usersList) {
        this.usersList = usersList;
    }
}
