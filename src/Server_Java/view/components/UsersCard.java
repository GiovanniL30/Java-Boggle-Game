package Server_Java.view.components;

import App.User;
import Server_Java.controller.AdminController;
import Server_Java.dataBase.Database;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UsersCard extends JPanel{
    public UsersCard(User user, AdminController adminController) {

        //Set panel properties
        setBorder(new EmptyBorder(20, 0, 0, 0));
        setMaximumSize(new Dimension(500, 400));
        setMaximumSize(getPreferredSize());
        setBackground(Color.white);

        // Panel for buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.white);
        GridLayout buttonsGrid = new GridLayout(1, 3);
        buttonsGrid.setHgap(10);
        buttonsGrid.setVgap(10);
        buttonsPanel.setLayout(buttonsGrid);

        // Panel for displaying account information
        JPanel accountInfo = new JPanel();
        accountInfo.setBackground(Color.white);
        accountInfo.setLayout(new GridLayout(4, 1));

        //Buttons
        shared.viewComponents.Button banButton = new shared.viewComponents.Button("Ban", new Dimension(100, 50), FontFactory.newPoppinsDefault(13));
        banButton.setForeground(Color.white);
        banButton.setBackground(ColorFactory.purple());

        shared.viewComponents.Button unbanButton = new shared.viewComponents.Button("Unban", new Dimension(100, 50), FontFactory.newPoppinsDefault(13));
        unbanButton.setForeground(Color.white);
        unbanButton.setBackground(ColorFactory.green());

        shared.viewComponents.Button deleteAccount = new shared.viewComponents.Button("Delete", new Dimension(100, 50), FontFactory.newPoppinsDefault(13));
        deleteAccount.setForeground(Color.white);
        deleteAccount.setBackground(ColorFactory.red());

        // Creating labels for account information
        JLabel userName = new JLabel("Username: " + user.userName);
        JLabel name = new JLabel("Name: " + user.firstName + " " + user.lastName);
        JLabel userPassword = new JLabel("Password: " + user.password);

        name.setFont(FontFactory.newPoppinsBold(14));
        userName.setFont(FontFactory.newPoppinsDefault(13));
        userPassword.setFont(FontFactory.newPoppinsDefault(13));

        // Add buttons to buttons panel
        buttonsPanel.add(unbanButton);
        buttonsPanel.add(banButton);
        buttonsPanel.add(deleteAccount);
        buttonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Add account info to account info panel
        accountInfo.add(name);
        accountInfo.add(userName);
        accountInfo.add(userPassword);

        JPanel rightSide = new JPanel();
        rightSide.setBackground(Color.white);
        GridLayout rightGrid = new GridLayout(1, 2);
        rightGrid.setVgap(10);
        rightGrid.setHgap(10);
        rightSide.setLayout(rightGrid);
        rightSide.add(accountInfo);
        rightSide.add(buttonsPanel);

        // Setting color and button states based on account status
        if (Database.isAccountBanned(user.userID)) {
            banButton.setEnabled(false);
            banButton.setBackground(Color.white);
        } else {
            unbanButton.setEnabled(false);
            unbanButton.setBackground(Color.white);
        }
        rightSide.setBorder(new EmptyBorder(0, 10, 0, 0));

        add(rightSide);

        // Action listeners for banning, unbanning, deleting, and editing account
        banButton.addActionListener(e -> adminController.banUser(user.userID));
        unbanButton.addActionListener(e -> adminController.unBanUser(user.userID));
        deleteAccount.addActionListener(e -> adminController.deleteUserAccount(user.userID));

    } // end AccountCard constructor
}
