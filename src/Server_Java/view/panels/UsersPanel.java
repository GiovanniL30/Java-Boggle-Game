package Server_Java.view.panels;

import App.User;
import Server_Java.controller.AdminController;
import Server_Java.view.components.SearchBar;
import Server_Java.view.components.UsersList;
import shared.utilities.FontFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.LinkedList;

public class UsersPanel extends JPanel {
    public UsersPanel(LinkedList<User> users, AdminController adminController) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();
        JLabel boggledUsers = new JLabel("Boggled Users");
        boggledUsers.setFont(FontFactory.newPoppinsBold(30));

        add(header);
        SearchBar searchbar = new SearchBar();
        add(searchbar);
        UsersList userList = new UsersList(users, adminController);
        add(userList);
    }
}
