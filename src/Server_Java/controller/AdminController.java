package Server_Java.controller;

import App.*;
import Server_Java.view.AdminMainFrame;
import org.omg.CORBA.ORB;

public class AdminController {
    private final ApplicationServer applicationServer;
    private final ORB orb;
    private AdminMainFrame adminMainFrame;

    public AdminController(ApplicationServer applicationServer, ORB orb) {
        this.applicationServer = applicationServer;
        this.orb = orb;

    }


    public void getTime(int time) {
        applicationServer.getTime(time);
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

    public void setAdminMainFrame(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;
    }
}
