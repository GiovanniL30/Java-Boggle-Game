package Client_Java.controller;

import App.*;
import Client_Java.utilities.ClientViews;
import Client_Java.view.MainFrame;
import Client_Java.view.panels.*;
import Server_Java.GameLobby;
import Server_Java.dataBase.Database;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;


public class ClientController extends ControllerPOA {

    private final ApplicationServer applicationServer;
    private final ORB orb;
    private MainFrame mainFrame;
    private User loggedInUser;
    private String gameLobby = "";


    public ClientController(ApplicationServer applicationServer, ORB orb) {
        this.applicationServer = applicationServer;
        this.orb = orb;

    }

    @Override
    public void receiveUpdates(App.ClientActions clientActions) {
        if (clientActions.equals(ClientActions.START_GAME)) {
            new Thread(() -> applicationServer.startGame(gameLobby)).start();
            changeFrame(ClientViews.GAME_LOBBY);
        }
    }

    @Override
    public void sendUpdates(App.ClientActions clientActions) {

    }

    @Override
    public void updateWaitingLobbyView(User user) {
        mainFrame.getWaitingLobby().updatePlayerList();
    }

    @Override
    public void setWaitingTime(int time) {
        mainFrame.getWaitingLobby().setTime(time);
    }

    @Override
    public void setGameTime(int time) {

    }

    @Override
    public void setRound(int round) {

    }

    @Override
    public void updatePlayerScore(String id, int newScore) {
        mainFrame.getGameStartedLobby().updatePlayerScores(id, newScore);
    }

    @Override
    public void receiveLetter(String[] letters) {

    }


    public void logIn(String userName, String password) {

        try {
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            User user = applicationServer.login(userName, password, ControllerHelper.narrow(rootPOA.servant_to_reference(this)));

            if (!user.userName.isEmpty()) {
                loggedInUser = user;

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if(!gameLobby.isEmpty()) {
                        leaveLobby(gameLobby);
                    }
                    applicationServer.logout(loggedInUser.userID);
                }));

                changeFrame(ClientViews.HOME_PAGE);
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(mainFrame, user.userID)).start();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void createAccount(User user) {


        Response response = applicationServer.createAccount(user);
        if (response.isSuccess) {
            new Thread(() -> JOptionPane.showMessageDialog(mainFrame, "Created Account Successfully")).start();
            changeFrame(ClientViews.LOGIN);
        } else {
            if (response.payload.extract_string().contains("User name already exist")) {
                mainFrame.getSignup().getUserName().enableError("Username already exist");
            } else {
                JOptionPane.showMessageDialog(mainFrame, response.payload.extract_string());
            }
        }
    }

    public void createNewLobby() {

        try {

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            Response response = applicationServer.createLobby(loggedInUser, ControllerHelper.narrow(rootPOA.servant_to_reference(this)));
            if (response.isSuccess) {
                gameLobby = response.payload.extract_string();
                changeFrame(ClientViews.WAIT_LOBBY);
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(mainFrame, response.payload.extract_string())).start();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void joinLobby(String lobbyId) {

        try {
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            Response response = applicationServer.joinLobby(loggedInUser, lobbyId, ControllerHelper.narrow(rootPOA.servant_to_reference(this)));
            if (response.isSuccess) {
                gameLobby = lobbyId;
                changeFrame(ClientViews.GAME_LOBBY);
            } else {
                JOptionPane.showMessageDialog(mainFrame, response.payload.extract_string());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public User[] lobbyPlayer(String lobbyId) {
        return applicationServer.getPlayers(lobbyId);
    }
    public void leaveLobby(String lobbyId) {
        Response response =  applicationServer.leaveLobby(loggedInUser, lobbyId);

        if(response.isSuccess) {
            gameLobby = "";
            changeFrame(ClientViews.HOME_PAGE);
        }

        new Thread(() -> JOptionPane.showMessageDialog(mainFrame, response.payload.extract_string())).start();
    }


    public void changeFrame(ClientViews clientViews) {
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() {

                switch (clientViews) {
                    case LOGIN: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setLogin(new Login(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getLogin(), 1);
                        break;
                    }
                    case SIGN_UP: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setSignup(new Signup(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getSignup(), 1);
                        break;
                    }
                    case HOME_PAGE: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setHomePage(new HomePage(ClientController.this));
                        mainFrame.getContentPane().add(mainFrame.getHomePage(), 1);
                        break;
                    }
                    case GAME_LOBBY: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setGameStartedLobby(new GameStartedLobby(ClientController.this, gameLobby));
                        mainFrame.getContentPane().add(mainFrame.getGameStartedLobby(), 1);
                        break;
                    }
                    case WAIT_LOBBY: {
                        mainFrame.getContentPane().remove(1);
                        mainFrame.setWaitingLobby(new WaitingLobby(ClientController.this, gameLobby));
                        mainFrame.getContentPane().add(mainFrame.getWaitingLobby(), 1);
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
                mainFrame.getContentPane().revalidate();
                mainFrame.getContentPane().repaint();
            }

        }.execute();
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
