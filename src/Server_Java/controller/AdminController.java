package Server_Java.controller;

import App.ApplicationServer;
import App.ClientActions;
import App.ControllerPOA;
import App.User;
import Server_Java.view.AdminMainFrame;
import org.omg.CORBA.ORB;

public class AdminController extends ControllerPOA {
    private final ApplicationServer applicationServer;
    private final ORB orb;
    private AdminMainFrame adminMainFrame;

    public AdminController(ApplicationServer applicationServer, ORB orb) {
        this.applicationServer = applicationServer;
        this.orb = orb;

    }

    @Override
    public void receiveUpdates(ClientActions clientActions) {

    }

    @Override
    public void sendUpdates(ClientActions clientActions) {

    }

    @Override
    public void updatePlayerListView() {

    }

    @Override
    public void setWaitingTime(int time) {

    }

    @Override
    public void setGameTime(int time) {

    }

    @Override
    public void setRound(int round) {

    }

    @Override
    public void updatePlayerScore(String id, int newScore) {

    }

    @Override
    public void receiveLetter(String[] letters) {

    }

    @Override
    public void endGameUpdate(User winner, int score) {

    }

    @Override
    public void stopIdleTime() {

    }

    @Override
    public void startIdleTime() {

    }

    @Override
    public void setIdleTimeLeft(String message) {

    }

    @Override
    public void removeWord(String word) {

    }

    public void setAdminMainFrame(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;
    }
}
