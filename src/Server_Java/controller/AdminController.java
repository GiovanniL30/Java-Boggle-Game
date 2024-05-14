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

import javax.swing.*;
import java.util.LinkedList;

public class AdminController {
    private final ApplicationServer applicationServer;
    private final ORB orb;
    private AdminMainFrame adminMainFrame;
    private LinkedList<User> users;

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
        Response response = applicationServer.banUser(userId);

        // handle the response
    }


    public void unBanUser(String userId) {
        Response response = applicationServer.unBanUser(userId);

        // handle the response
    }

    public void deleteUserAccount(String userId) {
        Response response = applicationServer.deleteUserAccount(userId);

        //handle the response
    }

    public void changeFrame(AdminViews adminViews) {
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {

                switch (adminViews) {
                    case SIGN_UP: {
                        adminMainFrame.getHeader().setVisible(false);
                        adminMainFrame.getContentPane().remove(1);
                        adminMainFrame.setSignUp(new Signup(AdminController.this));
                        adminMainFrame.getContentPane().add(adminMainFrame.getSignUp(), 1);
                        break;
                    }
                    case GAME_SETTINGS: {
                        adminMainFrame.getHeader().setVisible(true);
                        adminMainFrame.getContentPane().remove(1);
                        adminMainFrame.setGameSettings(new GameSettings(AdminController.this));
                        adminMainFrame.getContentPane().add(adminMainFrame.getGameSettings(), 1);
                        break;
                    }
                    case PLAYERS: {
                        adminMainFrame.getHeader().setVisible(true);
                        adminMainFrame.getContentPane().remove(1);
                        adminMainFrame.setUsersPanel(new UsersPanel(users, AdminController.this));
                        adminMainFrame.getContentPane().add(adminMainFrame.getUsersPanel(), 1);
                        break;
                    }
                    case HOME_PAGE: {
                        adminMainFrame.getHeader().setVisible(true);
                        adminMainFrame.getContentPane().remove(1);
                        adminMainFrame.setAdminHomePage(new AdminHomePage(AdminController.this));
                        adminMainFrame.getContentPane().add(adminMainFrame.getAdminHomePage(), 1);
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
