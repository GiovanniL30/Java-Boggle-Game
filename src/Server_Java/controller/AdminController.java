package Server_Java.controller;

import App.*;
import Client_Java.controller.ClientController;
import Client_Java.utilities.ClientViews;
import Client_Java.view.panels.*;
import Server_Java.utilities.AdminViews;
import Server_Java.view.AdminMainFrame;
import Server_Java.view.panels.AdminHomePage;
import Server_Java.view.panels.GameSettings;
import Server_Java.view.panels.Signup;
import Server_Java.view.panels.UsersPanel;
import org.omg.CORBA.ORB;
import shared.utilities.UtilityMethods;

import javax.swing.*;
import java.util.LinkedList;

public class AdminController {
    private final ApplicationServer applicationServer;
    private final ORB orb;
    private AdminMainFrame adminMainFrame;

    public AdminController(ApplicationServer applicationServer, ORB orb) {
        this.applicationServer = applicationServer;
        this.orb = orb;

    }


    public int[] getTime() {
        return applicationServer.getTime();
    }


    public void updateGameTime(int time) {
        applicationServer.updateGameTime(time);
    }


    public void updateWaitingTime(int time) {
        applicationServer.updateWaitingTime(time);
    }


    public void banUser(String userId) {
        try {
            Response response = applicationServer.banUser(userId);
            if (response.isSuccess) {
                changeFrame(AdminViews.PLAYERS);
            }
            JOptionPane.showMessageDialog(null, response.payload.extract_string());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void unBanUser(String userId) {
        try {
            Response response = applicationServer.unBanUser(userId);
            if (response.isSuccess) {
                changeFrame(AdminViews.PLAYERS);
            }
            JOptionPane.showMessageDialog(null, response.payload.extract_string());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserAccount(String userId) {
        try {
            Response response = applicationServer.deleteUserAccount(userId);
            if (response.isSuccess) {
                changeFrame(AdminViews.PLAYERS);

            }

            JOptionPane.showMessageDialog(null, response.payload.extract_string());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changeFrame(AdminViews adminViews) {
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {

                switch (adminViews) {
                    case SIGN_UP: {
                        adminMainFrame.getContentPane().remove(1);
                        adminMainFrame.setSignUp(new Signup(AdminController.this));
                        adminMainFrame.getContentPane().add(adminMainFrame.getSignUp(), 1);
                        adminMainFrame.getAdminHeader().setVisible(false);
                        break;
                    }
                    case GAME_SETTINGS: {
                        adminMainFrame.getContentPane().remove(1);
                        adminMainFrame.setGameSettings(new GameSettings(AdminController.this));
                        adminMainFrame.getContentPane().add(adminMainFrame.getGameSettings(), 1);
                        adminMainFrame.getAdminHeader().setVisible(true);
                        adminMainFrame.getAdminHeader().setText("Game Settings");
                        adminMainFrame.getAdminHeader().getCreateAccount().setVisible(false);
                        break;
                    }
                    case PLAYERS: {
                        adminMainFrame.getContentPane().remove(1);
                        adminMainFrame.setUsersPanel(new UsersPanel(AdminController.this));
                        adminMainFrame.getContentPane().add(adminMainFrame.getUsersPanel(), 1);
                        adminMainFrame.getAdminHeader().setVisible(true);
                        adminMainFrame.getAdminHeader().setText("Boggled Players");
                        adminMainFrame.getAdminHeader().getCreateAccount().setVisible(true);
                        break;
                    }
                    case HOME_PAGE: {
                        adminMainFrame.getContentPane().remove(1);
                        adminMainFrame.setAdminHomePage(new AdminHomePage(AdminController.this));
                        adminMainFrame.getContentPane().add(adminMainFrame.getAdminHomePage(), 1);
                        adminMainFrame.getAdminHeader().setVisible(false);
                        break;
                    }
                    default: {
                        System.out.println("Error changing frame");
                    }
                }

                return null;
            }

            @Override
            protected void done() {
                adminMainFrame.getContentPane().revalidate();
                adminMainFrame.getContentPane().repaint();
            }

        }.execute();
    }

    public void setAdminMainFrame(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;
    }
}
