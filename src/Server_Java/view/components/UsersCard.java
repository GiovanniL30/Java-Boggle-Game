package Server_Java.view.components;

import App.User;
import Server_Java.controller.AdminController;
import Server_Java.dataBase.Database;
import Server_Java.utilities.AdminViews;
import shared.utilities.ColorFactory;
import shared.utilities.FontFactory;
import shared.viewComponents.FilledButton;

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
        FilledButton banButton = new FilledButton("Ban", new Dimension(100, 50), FontFactory.newPoppinsDefault(13), ColorFactory.mahogany(),Color.white);
        FilledButton unbanButton = new FilledButton("Unban", new Dimension(100, 50), FontFactory.newPoppinsDefault(13), ColorFactory.cream(), ColorFactory.mahogany());

        shared.viewComponents.Button deleteAccount = new shared.viewComponents.Button("Delete", new Dimension(100, 50), FontFactory.newPoppinsDefault(13));
        deleteAccount.setForeground(Color.white);
        deleteAccount.setBackground(ColorFactory.red());

        // Creating labels for account information
        JLabel userName = new JLabel("Username: " + user.userName);
        JLabel name = new JLabel("Name: " + user.firstName + " " + user.lastName);
        JLabel userPassword = new JLabel("Password: " + user.password);
        JLabel isOnline = new JLabel(user.isOnline ? "Online" : "Offline");
        isOnline.setFont(FontFactory.newPoppinsDefault(13));

        if(user.isOnline) {
            isOnline.setForeground(Color.green);
        }else {
            isOnline.setForeground(Color.red);
        }

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
        accountInfo.add(isOnline);

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
        banButton.addActionListener(e -> {

            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to ban " + user.userName + "?", "Ban User", JOptionPane.YES_NO_OPTION);

            if (response  == JOptionPane.YES_OPTION) {
                adminController.banUser(user.userID);
            }
        });

        unbanButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to unban " + user.userName + "?", "Unban User", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                adminController.unBanUser(user.userID);
            }
        });

        deleteAccount.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + user.userName + "?", "Delete User", JOptionPane.YES_NO_OPTION);

            if (response  == JOptionPane.YES_OPTION) {
                adminController.deleteUserAccount(user.userID);
            }
        });

    } // end AccountCard constructor
}
